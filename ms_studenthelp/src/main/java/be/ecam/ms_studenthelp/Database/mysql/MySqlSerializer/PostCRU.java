package be.ecam.ms_studenthelp.Database.mysql.MySqlSerializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import be.ecam.ms_studenthelp.Database.mysql.MySqlDatabase;
import be.ecam.ms_studenthelp.Interfaces.*;
import be.ecam.ms_studenthelp.Object.*;

public class PostCRU {

    private Connection con;

    public PostCRU(Connection con_){
        con = con_;
    }

    public IPost GetPost(String uuid){
        return GetPost(uuid, 1,null);
    }

    //recursive function the create a post and get a list of its children
    private IPost GetPost(String uuid,int recurstion,IPost parent){

        // select the data to create based on it's uuid
        String query = String.format(
            "SELECT e.id,e.authorId,e.date,e.lastModif,pt.content,pt.parent FROM `mssh_elem` as e INNER JOIN `mssh_Post` as pt ON pt.id = e.id WHERE e.id = '%s'",
            uuid
        );

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){


                LocalDateTime lastModif = null;
                java.sql.Timestamp lastModif_ts = rs.getTimestamp("lastModif");
                if(lastModif_ts != null){
                    lastModif = lastModif_ts.toLocalDateTime();
                }

                Post pt = new Post(
                    rs.getString("id"),
                    rs.getString("authorId"),
                    rs.getString("content"),
                    rs.getTimestamp("date").toLocalDateTime(),
                    lastModif,
                    parent
                );

                ArrayList<IPost> children = new ArrayList<IPost>();

                //get it's children
                if(recurstion > 0){
                    //select the data to create a post base on it's parent id
                    String query_child = String.format(
                        "SELECT e.id FROM `mssh_elem` as e INNER JOIN `mssh_Post` as pt ON pt.id = e.id WHERE pt.parent = '%s'",
                        rs.getString("id")
                    );

                    Statement st_child = con.createStatement();
                    ResultSet rs_child = st_child.executeQuery(query_child);

                    while(rs_child.next()){
                        IPost pt_child = GetPost(rs_child.getString("id"), recurstion-1, pt);
                        if(pt_child != null){
                            children.add(pt_child);
                        }
                    }

                }

                pt.setChildren(children);

                return pt;

            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
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

        return MySqlDatabase.UpdateQuery(queries);
    }

    public int UpdatePost(IPost pt){

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String datetime = pt.getLastModif().format(formatter).replace("T", " ");

        String query_elem = String.format(
            "UPDATE `mssh_elem` SET `lastModif`='%s' WHERE `id` = '%s';\n",
            datetime,
            pt.getId()
        );

        String query_ft = String.format(
            "UPDATE `mssh_Post` SET `content`='%s' WHERE `id` = '%s';\n",
            pt.getContent(),
            pt.getId()
        );
            
        ArrayList<String> queries = new ArrayList<String>();
        queries.add(query_elem);
        queries.add(query_ft);

        return MySqlDatabase.UpdateQuery(queries);
    }

}
