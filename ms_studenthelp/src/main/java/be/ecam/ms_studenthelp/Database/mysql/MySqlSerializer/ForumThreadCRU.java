package be.ecam.ms_studenthelp.Database.mysql.MySqlSerializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import be.ecam.ms_studenthelp.Database.mysql.MySqlDatabase;
import be.ecam.ms_studenthelp.Interfaces.*;
import be.ecam.ms_studenthelp.Object.ForumThread;

public class ForumThreadCRU {

    private Connection con;
    private PostCRU PCRU;

    public ForumThreadCRU(Connection con_,PostCRU PCRU_){
        con = con_;
        PCRU = PCRU_;
    }

    //TODO : ADD tag managment
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

        
        int res = MySqlDatabase.UpdateQuery(queries);
        if(ft.getChild()!= null){
            String query_update = String.format(
               "UPDATE `mssh_ForumThread` SET `child`='%s' WHERE `id` = '%s'" ,
               ft.getChild().getId(),
               ft.getId()
            );
            
            ArrayList<String> queries_update = new ArrayList<String>();
            queries_update.add(query_update);

            res += MySqlDatabase.UpdateQuery(queries_update);
            res += PCRU.CreatePost(ft.getChild());
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

        return MySqlDatabase.UpdateQuery(queries);
    }

    public IForumThread GetForumThread(String uuid){

        String query = String.format(
            "SELECT e.id,e.authorId,e.date,e.lastModif,ft.title as ft_title,ft.answered,ft.child,cat.title as cat_title FROM `mssh_elem` as e INNER JOIN `mssh_ForumThread` as ft ON ft.id = e.id INNER JOIN `mssh_category` as cat ON cat.id = ft.category WHERE e.id = '%s'",
            uuid
        );

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {

                LocalDateTime lastModif = null;
                java.sql.Timestamp lastModif_ts = rs.getTimestamp("lastModif");
                if(lastModif_ts != null){
                    lastModif = lastModif_ts.toLocalDateTime();
                }

                IPost child = null;
                String Child_id = (String)rs.getObject("child");
                if(Child_id != null){
                    child = PCRU.GetPost(Child_id);
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
            return null;
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
            return null;
        }

        return ft_list;
    }

}
