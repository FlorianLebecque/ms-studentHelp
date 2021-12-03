package be.ecam.ms_studenthelp.Database.mysql;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

import be.ecam.ms_studenthelp.Database.*;
import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Interfaces.IReaction;
import be.ecam.ms_studenthelp.Object.ForumThread;
import be.ecam.ms_studenthelp.Object.Post;
import be.ecam.ms_studenthelp.Object.Reaction;

public class MySqlDatabase implements IIODatabaseObject {


    static private Connection con = null;

    public boolean connect(){
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception E) {
            System.err.println("Unable to load driver");
            E.printStackTrace();
        }

        String MySQLURL = "jdbc:mysql://localhost/ms_studenthelp";
        String databseUserName = "dummy";
        String databasePassword = "1234";

        try{
            con = DriverManager.getConnection(MySQLURL,databseUserName,databasePassword);
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



    public int CreateForumThread(IForumThread ft_){

        ForumThread ft = (ForumThread)ft_;
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String datetime = ft.getDate().format(formatter).replace("T", " ");
            String query = String.format(
                "INSERT INTO `mssh_object`(`id`, `type`, `datetime`, `author`) VALUES ('%s',%d,'%s','%s')",
                ft.getId(),
                1,
                datetime,
                ft.getAuthorId()
            );

            

            //INSERT INTO `mssh_object`(`id`, `type`, `datetime`, `author`) VALUES ('13a19100-de22-42a9-92ea-7cbb527fc106','1','2021-11-330 13:11:14','0719bd76-427d-4487-a26c-5eab209f9ef9')
            Statement ps = con.createStatement(); 
            ps.executeUpdate(query);

            
            Dictionary<String, String> dic = new Hashtable<String, String>();
            dic.put("title", ft.getTitle());
            dic.put("catergory", ft.getCategory());
            String answered = ft.getAnswer()?"true":"false";
            dic.put("answered", answered);
            
            Enumeration<String> ekey = dic.keys();
            while(ekey.hasMoreElements()){
                String key = ekey.nextElement();
                String cur_query =  String.format(
                    "INSERT INTO `mssh_objectmeta`(`id_object`, `meta_key`, `type`, `meta_value`) VALUES ('%s','%s','%s','%s')",
                    ft.getId(),
                    key,
                    "string",
                    dic.get(key)
                );

                Statement meta_statement = con.createStatement(); 
                meta_statement.executeUpdate(cur_query);

            }
                    
            return 1;
        } catch (Exception e) {
            e.printStackTrace();

            return -1;
        }


    }

    public int UpdateForumThread(IForumThread ft) {
        // TODO Auto-generated method stub
        return 0;
    }

    public ForumThread GetForumThread(String uuid){

        String query = "SELECT * FROM `mssh_object` WHERE `type` = 1";

        //SELECT * FROM `mssh_object` WHERE `id` = uuid



        //ForumThread ft = new ForumThread(id_, title_, tags_, authorId_, date_, category_, answered_, children_);

        return new ForumThread("tqset", "id1", "test");
    }


    public List<ForumThread> GetForumThreads(int nbr_per_page,int page_index){
        String query = "SELECT * FROM `mssh_object` WHERE `type` = 1";

        //<YOUR CLASS>.getClass().getDeclaredFields()

        try {
            Statement st = con.createStatement();
      
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
        
            // iterate through the java resultset
            while (rs.next()) {

                Dictionary<String, Object> ForumThreadMap = new Hashtable<String, Object>();

                ForumThreadMap.put("id",rs.getString("id"));
                ForumThreadMap.put("date",rs.getString("datetime"));
                ForumThreadMap.put("authorId",rs.getString("author"));

                //load the meta data
                String cur_query = String.format(
                    "SELECT * FROM `mssh_objectmeta` WHERE `id_object` = '%s'",
                    ForumThreadMap.get("id")
                );

                Statement metatST = con.createStatement();
                ResultSet metaRS = metatST.executeQuery(cur_query);

                while(metaRS.next()){
                    ForumThreadMap.put(metaRS.getString("meta_key"), metaRS.getString("meta_value"));
                }
                    

            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        return new ArrayList<ForumThread>();
    }



    public Post GetPost(String uuid){
        return new Post();
    }

    public int CreatePost(IPost pt){
        return 0;
    }

    public int UpdatePost(IPost pt){
        return 0;
    }

    public List<Post> GetPosts(int nbr_per_page,int page_index){
        return new ArrayList<Post>();
    }


    public IReaction GetReaction(IPost post, String authorId){
        String postId = post.getId();
        try {
            String cur_query = String.format(
                "SELECT value FROM `mssh_reaction` WHERE `post_id` = '%s' AND `author` = '%s'",
                postId,
                authorId
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(cur_query);

            if (rs.next()) {
                int value = rs.getInt("value");
                return new Reaction(postId, authorId, value);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public List<IReaction> GetReactions(IPost post){
        String postId = post.getId();
        try {
            String cur_query = String.format(
                "SELECT author, value FROM `mssh_reaction` WHERE `post_id` = '%s'",
                postId
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(cur_query);

            ArrayList<IReaction> reactions = new ArrayList<IReaction>();
            while(rs.next()) {
                String authorId = rs.getString("author");
                int value = rs.getInt("value");
                Reaction reaction = new Reaction(postId, authorId, value);
                reactions.add(reaction);
            }

            return reactions;
        } catch (Exception e) {
            return null;
        }
    }

    public IReaction CreateReaction(IReaction reaction){
        try {
            String query = String.format(
                "INSERT INTO `mssh_reaction`(`post_id`, `author`, `value`) VALUES ('%s','%s',%d)",
                reaction.getPostId(),
                reaction.getAuthorId(),
                reaction.getValue()
            );
            Statement ps = con.createStatement();
            ps.executeUpdate(query);

            return reaction;
        } catch (Exception e) {
            return null;
        }
    }

    public IReaction UpdateReaction(IReaction reaction){
        try {
            String query = String.format(
                "UPDATE `mssh_reaction` SET `value` = %d WHERE `post_id` = '%s' AND `author` = '%s'",
                reaction.getPostId(),
                reaction.getAuthorId(),
                reaction.getValue()
            );
            Statement ps = con.createStatement();
            ps.executeUpdate(query);

            return reaction;
        } catch (Exception e) {
            return null;
        }
    }

    public IReaction DeleteReaction(IReaction reaction){
        try {
            String query = String.format(
                "DELETE FROM `mssh_reaction` WHERE  `post_id` = '%s' AND `author` = '%s'",
                reaction.getPostId(),
                reaction.getAuthorId()
            );
            Statement ps = con.createStatement();
            ps.executeUpdate(query);

            return reaction;
        } catch (Exception e) {
            return null;
        }
    }

}
