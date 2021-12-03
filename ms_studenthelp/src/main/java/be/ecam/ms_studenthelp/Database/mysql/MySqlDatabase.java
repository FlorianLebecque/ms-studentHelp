package be.ecam.ms_studenthelp.Database.mysql;

import java.util.ArrayList;
import java.util.Dictionary;
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

    private void LoadMetaDictFromID(Dictionary<String,Object> dic,String id){

        try {
             //load the meta data
             String cur_query = String.format(
                "SELECT * FROM `mssh_objectmeta` WHERE `id_object` = '%s'",
                id
            );

            Statement metatST = con.createStatement();
            ResultSet metaRS = metatST.executeQuery(cur_query);

            while(metaRS.next()){
                dic.put(metaRS.getString("meta_key"), metaRS.getString("meta_value"));
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
    }


    private int UpdateQuery(List<String> queries){
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

    public int CreateForumThread(IForumThread ft_){

        ForumThread ft = (ForumThread)ft_;
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        String datetime = ft.getDate().format(formatter).replace("T", " ");

        String query_elem = String.format(
            "INSERT INTO `mssh_elem`(`id`, `authorId`, `date`) VALUES ('%s','%s','%s');\n",
            ft.getId(),
            ft.getAuthorId(),
            datetime
        );

        String query_ft = String.format(
            "INSERT INTO `mssh_ForumThread`(`id`, `title`, `category`, `answered`) VALUES ('%s','%s',(SELECT `id` FROM `mssh_category` WHERE `title` = '%s'),0);\n",
            ft.getId(),
            ft.getTitle(),
            ft.getCategory()
        );

        ArrayList<String> queries = new ArrayList<String>();
        queries.add(query_elem);
        queries.add(query_ft);

        return UpdateQuery(queries);
    }

    public int UpdateForumThread(IForumThread ft_) {
        ForumThread ft = (ForumThread)ft_;
        
        int answered = 0;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String datetime = ft.getModification().format(formatter).replace("T", " ");

        String query_elem = String.format(
            "UPDATE `mssh_elem` SET `lastModif`='%s' WHERE `id` = '%s';\n",
            datetime,
            ft.getId()
        );

        String query_ft = String.format(
            "UPDATE `mssh_ForumThread` SET `title`='%s',`category`= (SELECT `id` FROM `mssh_category` WHERE `title` = '%s') ,`answered` = %d WHERE `id` = '%s';\n",
            ft.getTitle(),
            ft.getCategory(),
            answered,
            ft.getId()
        );
            
        ArrayList<String> queries = new ArrayList<String>();
        queries.add(query_elem);
        queries.add(query_ft);

        return UpdateQuery(queries);
    }

    public ForumThread GetForumThread(String uuid){

        String query = String.format(
            "SELECT e.id,e.authorId,e.date,e.lastModif,ft.title as ft_title,ft.answered,ft.child,cat.title as cat_title FROM `mssh_elem` as e INNER JOIN `mssh_ForumThread` as ft ON ft.id = e.id INNER JOIN `mssh_category` as cat ON cat.id = ft.category WHERE e.id = '%s'",
            uuid
        );

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                ForumThread ft = new ForumThread(
                    rs.getString("id"),
                    rs.getString("authorId"),
                    rs.getString("ft_title"),
                    rs.getString("cat_title")
                );
                

                ft.date = rs.getTimestamp("date").toLocalDateTime();

                java.sql.Timestamp lastModif =  (java.sql.Timestamp)rs.getObject("lastModif");
                if(lastModif != null){
                    ft.lastModif = lastModif.toLocalDateTime();
                }

                int answered = rs.getInt("answered");
                ft.answered = answered != 0;

                String Child_id = (String)rs.getObject("child");
                if(Child_id != null){
                    ft.child = GetPost(Child_id);
                }


                return ft;
            }

        } catch (Exception e) {
            System.out.println(query);
        }

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

                //Dictionary<String,Object> ForumThreadMap = CreateDicFromRS(rs);

                //LoadMetaDictFromID(ForumThreadMap,(String)ForumThreadMap.get("id"));



            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        return new ArrayList<ForumThread>();
    }



    public Post GetPost(String uuid){
        return new Post("dqd");
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
                "SELECT value FROM `mssh_reactions` WHERE `postId` = '%s' AND `authorId` = '%s'",
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
            e.printStackTrace();
            return null;
        }
    }

    public List<IReaction> GetReactions(IPost post){
        String postId = post.getId();
        try {
            String cur_query = String.format(
                "SELECT authorId, value FROM `mssh_reactions` WHERE `postId` = '%s'",
                postId
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(cur_query);

            ArrayList<IReaction> reactions = new ArrayList<IReaction>();
            while(rs.next()) {
                String authorId = rs.getString("authorId");
                int value = rs.getInt("value");
                Reaction reaction = new Reaction(postId, authorId, value);
                reactions.add(reaction);
            }

            return reactions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public IReaction CreateReaction(IReaction reaction){
        try {
            String query = String.format(
                "INSERT INTO `mssh_reactions`(`postId`, `authorId`, `value`) VALUES ('%s','%s',%d)",
                reaction.getPostId(),
                reaction.getAuthorId(),
                reaction.getValue()
            );
            Statement ps = con.createStatement();
            ps.executeUpdate(query);

            return reaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public IReaction UpdateReaction(IReaction reaction){
        try {
            String query = String.format(
                "UPDATE `mssh_reactions` SET `value` = %d WHERE `postId` = '%s' AND `authorId` = '%s'",
                reaction.getPostId(),
                reaction.getAuthorId(),
                reaction.getValue()
            );
            Statement ps = con.createStatement();
            ps.executeUpdate(query);

            return reaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public IReaction DeleteReaction(IReaction reaction){
        try {
            String query = String.format(
                "DELETE FROM `mssh_reactions` WHERE  `postId` = '%s' AND `authorId` = '%s'",
                reaction.getPostId(),
                reaction.getAuthorId()
            );
            Statement ps = con.createStatement();
            ps.executeUpdate(query);

            return reaction;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
