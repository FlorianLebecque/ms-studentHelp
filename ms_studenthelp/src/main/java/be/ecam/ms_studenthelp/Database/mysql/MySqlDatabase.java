package be.ecam.ms_studenthelp.Database.mysql;

import java.util.List;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.Statement;


import be.ecam.ms_studenthelp.Database.*;
import be.ecam.ms_studenthelp.Database.mysql.MySqlSerializer.*;
import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Interfaces.IReaction;

public class MySqlDatabase implements IIODatabaseObject {

    /*
     *  Implementation of IIODatabaseObject 
     * 
     *      CRU  -> Create, Read, Update
     *      CRUD -> Create, Read, Update, Delete
     * 
     *      Made of a few serialyzer
     *          -> FTCRU -> ForumThread CRU
     *          -> PCRU  -> Post CRU
     *          -> RCRUD -> Reaction CRUD
     *          -> ...
     * 
     *      TODO
     *          - Use Application.properties to get db credentials
     *              or use the intergrated ORM of springboot 
     * 
     *          - the paginatation is not implemented
     *          

            DATABASE

     *          All the object are stored in the mysql table (see ms_studenthelp.png)
     *            [mssh_elem] -> store data from Post AND Thread
     *                - Id        :  the post or thread id
     *                - authorId  : the id of the creator
     *                - date      : date of creation
     *                - lastModif : date of the last modification
     *   
     *            [mssh_forumthread] -> store the data of a thread
     *                - Id        : link to [mssh_elem]
     *                - category  : link to the category table
     *                - Child     : link to the first post of the thread
     *            
     *            [mssh_post] -> store the data of a post
     *                - Id        : ...
     *                - Parent    : if null -> the post is the first post of a thread
     *                              if not null -> link to the id of the parent post  (tree like structure)
     *   
     *            [mssh_reactions] -> list of all the reaction on the posts
     *            [mssh_ft_tags]   -> list of all the tags on the threads
     *            [mssh_category]  -> list of all the category
     */

    static private Connection con = null;

    private ForumThreadCRU FTCRU;
    private PostCRU PCRU;
    private ReactionCRUD RCRUD;
    private CategoryManager categoryManager;


    public MySqlDatabase(){
        this.connect();

        PCRU  = new PostCRU(con);
        FTCRU = new ForumThreadCRU(con,PCRU);
        RCRUD = new ReactionCRUD(con);
        categoryManager = new CategoryManager(con);
    }

    protected void finalize(){  
        disconnect();
    }            

    public boolean connect(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception E) {
            System.err.println("Unable to load driver");
            E.printStackTrace();
        }

        String MySQLURL = "jdbc:mysql://localhost/ms_studenthelp";
        String databaseUserName = "dummy";
        String databasePassword = "1234";

        try{
            con = DriverManager.getConnection(MySQLURL,databaseUserName,databasePassword);
            return con!=null;

        }catch(Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    public boolean isConnected(){
        return con != null;
    }

    public void disconnect(){
        try{
            con.commit();
            con.close();
        }catch (Exception E) {
            System.err.println("Unable to load driver");
            E.printStackTrace();
        }
    }

    /*
     *  Take a list of mysql queries, encapsulate them in a commit 
     *  and execute them in a sequence
     */
    public static int UpdateQuery(List<String> queries){
        try {

            Statement ps = con.createStatement(); 
            ps.executeUpdate("START TRANSACTION;");

            for (String query : queries) {
                ps.executeUpdate(query);
            }

            ps.executeUpdate("COMMIT;");
      
            return 1;
        } catch (Exception e) {
            
            e.printStackTrace();

            return -1;
        }
    }

    public int CreateForumThread(IForumThread ft){

        return FTCRU.CreateForumThread(ft);
    }

    public int UpdateForumThread(IForumThread ft) {
        
        return FTCRU.UpdateForumThread(ft);
    }

    public IForumThread GetForumThread(String uuid){

        return FTCRU.GetForumThread(uuid);
    }

    public List<IForumThread> GetForumThreads(int nbr_per_page,int page_index){

        return FTCRU.GetForumThreads(nbr_per_page, page_index);
    }

    public IPost GetPost(String uuid){
        return PCRU.GetPost(uuid);
    }

    public int CreatePost(IPost pt){

        return PCRU.CreatePost(pt);
    }

    public int UpdatePost(IPost pt){

        return PCRU.UpdatePost(pt);
    }

    public IReaction GetReaction(IPost post, String authorId){

        return RCRUD.GetReaction(post, authorId);
    }

    public List<IReaction> GetReactions(IPost post){
        
        return RCRUD.GetReactions(post);
    }

    public IReaction CreateReaction(IReaction reaction){

        return RCRUD.CreateReaction(reaction);
    }

    public IReaction UpdateReaction(IReaction reaction){
        
        return RCRUD.UpdateReaction(reaction);
    }

    public IReaction DeleteReaction(IReaction reaction){
        
        return RCRUD.DeleteReaction(reaction);
    }

    public List<String> GetCategories() {
        return categoryManager.GetCategories();
    }

}
