package be.ecam.ms_studenthelp.Database.mysql;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public int CreateForumThread(IForumThread ft){

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


        int res = UpdateQuery(queries);
        if(ft.getChildren()!= null){
            String query_update = String.format(
                    "UPDATE `mssh_ForumThread` SET `child`='%s' WHERE `id` = '%s'" ,
                    ft.getChildren().getId(),
                    ft.getId()
            );

            ArrayList<String> queries_update = new ArrayList<String>();
            queries_update.add(query_update);

            res += UpdateQuery(queries_update);
            res += CreatePost(ft.getChildren());
        }

        return res;
    }

    public int UpdateForumThread(IForumThread ft) {

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

    public IForumThread GetForumThread(String uuid){

        String query = String.format(
                "SELECT e.id,e.authorId,e.date,e.lastModif,ft.title as ft_title,ft.answered,ft.child,cat.title as cat_title FROM `mssh_elem` as e INNER JOIN `mssh_ForumThread` as ft ON ft.id = e.id INNER JOIN `mssh_category` as cat ON cat.id = ft.category WHERE e.id = '%s'",
                uuid
        );

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println(query);
            System.out.println("RS");
            System.out.println(rs);
            System.out.println("RSFin");


            while (rs.next()) {

                LocalDateTime lastModif = null;
                java.sql.Timestamp lastModif_ts =  rs.getTimestamp("lastModif");
                if(lastModif_ts != null){
                    lastModif = lastModif_ts.toLocalDateTime();
                }


                IPost child = null;
                String Child_id = (String)rs.getObject("child");
                if(Child_id != null){
                    child = GetPost(Child_id);
                }

                String query_tag = String.format(
                        "SELECT `tag` FROM `mssh_FT_tags` WHERE `id` = '%s'",
                        rs.getString("id")
                );

                Statement st_tag = con.createStatement();
                ResultSet rs_tag = st_tag.executeQuery(query_tag);
                ArrayList<String> tag_list = new ArrayList<String>();
                while(rs_tag.next()){
                    tag_list.add(rs_tag.getString("tag"));
                }


                ForumThread ft = new ForumThread(
                        rs.getString("id"),
                        rs.getString("authorId"),
                        rs.getString("ft_title"),
                        rs.getString("cat_title"),
                        tag_list,
                        rs.getTimestamp("date").toLocalDateTime(),
                        lastModif,
                        rs.getInt("answered") != 0,
                        child
                );

                return ft;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<IForumThread> GetForumThreads(int nbr_per_page,int page_index){
        String query = "SELECT e.id FROM `mssh_elem` as e INNER JOIN `mssh_ForumThread` as ft ON ft.id = e.id INNER JOIN `mssh_category` as cat ON cat.id = ft.category";

        ArrayList<IForumThread> ft_list = new ArrayList<>();

        try {
            Statement st = con.createStatement();

            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                IForumThread ft = GetForumThread(rs.getString("id"));

                if(ft != null){
                    ft_list.add(ft);
                }

            }
        } catch (Exception e) {
            //TODO: handle exception
        }

        return ft_list;
    }



    public IPost GetPost(String uuid){
        return GetPost(uuid, 1);
    }

    private IPost GetPost(String uuid,int recurstion){
        String query = String.format(
                "SELECT e.id,e.authorId,e.date,e.lastModif,pt.content,pt.parent FROM `mssh_elem` as e INNER JOIN `mssh_Post` as pt ON pt.id = e.id WHERE e.id = '%s'",
                uuid
        );





        return new Post("dqd");
    }

    public int CreatePost(IPost pt){

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        String datetime = pt.getDatePosted().format(formatter).replace("T", " ");

        String query_elem = String.format(
                "INSERT INTO `mssh_elem`(`id`, `authorId`, `date`) VALUES ('%s','%s','%s');\n",
                pt.getId(),
                pt.getAuthorId(),
                datetime
        );

        String query_ft = String.format(
                "INSERT INTO `mssh_Post`(`id`, `content`) VALUES ('%s','%s');\n",
                pt.getId(),
                pt.getContent()
        );

        String query_setParent = "";

        IPost parent = pt.getParent();
        if(parent != null){
            query_setParent = String.format(
                    "UPDATE `mssh_Post` SET `parent`='%s' WHERE `id` = '%s'",
                    parent.getId(),
                    pt.getId()
            );
        }

        ArrayList<String> queries = new ArrayList<String>();
        queries.add(query_elem);
        queries.add(query_ft);

        if(query_setParent != ""){
            queries.add(query_setParent);
        }

        return UpdateQuery(queries);
    }

    public int UpdatePost(IPost pt){
        return 0;
    }

    public List<IPost> GetPosts(int nbr_per_page,int page_index){
        return new ArrayList<IPost>();
    }


    public IReaction GetReaction(IPost post, String authorId){
        String postId = post.getId();
        try {
            String cur_query = String.format(
                    "SELECT value FROM `mssh_reaction` WHERE `postId` = '%s' AND `authorId` = '%s'",
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
                    "SELECT author, value FROM `mssh_reaction` WHERE `postId` = '%s'",
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
            return null;
        }
    }

    public IReaction CreateReaction(IReaction reaction){
        try {
            String query = String.format(
                    "INSERT INTO `mssh_reaction`(`postId`, `authorId`, `value`) VALUES ('%s','%s',%d)",
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
                    "UPDATE `mssh_reaction` SET `value` = %d WHERE `postId` = '%s' AND `authorId` = '%s'",
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
                    "DELETE FROM `mssh_reaction` WHERE  `postId` = '%s' AND `authorId` = '%s'",
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