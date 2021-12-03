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


    static private Connection con = null;

    private ForumThreadCRU FTCRU;
    private PostCRU PCRU;
    private ReactionCRUD RCRUD;

    public MySqlDatabase(){
        PCRU  = new PostCRU(con);
        FTCRU = new ForumThreadCRU(con,PCRU);
        RCRUD = new ReactionCRUD(con);
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

    public void disconnect(){
        try{
            con.commit();
            con.close();
        }catch (Exception E) {
            System.err.println("Unable to load driver");
            E.printStackTrace();
        }
    }

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

}
