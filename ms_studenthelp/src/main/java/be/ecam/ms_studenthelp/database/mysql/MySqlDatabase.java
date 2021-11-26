package be.ecam.ms_studenthelp.database.mysql;

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

import be.ecam.ms_studenthelp.Object.ForumThread;
import be.ecam.ms_studenthelp.Object.Post;
import be.ecam.ms_studenthelp.database.*;

public class MySqlDatabase implements IODatabaseObject {


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

    public int CreateForumThread(ForumThread ft){

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

    public ForumThread GetForumThread(String uuid){

        String query = "SELECT * FROM `mssh_object` WHERE `type` = 1";

        return new ForumThread("tqset", "id1", "test");
    }

    public List<ForumThread> GetForumThreads(){
        String query = "SELECT * FROM `mssh_object` WHERE `type` = 1";

        try {
            Statement st = con.createStatement();
      
            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
        
            // iterate through the java resultset
            while (rs.next()) {
                String id = rs.getString("id");
                String datetimestr = rs.getString("datetime");
                String author = rs.getString("author");
        
                //load the meta data
                String cur_query = String.format(
                    "SELECT * FROM `mssh_objectmeta` WHERE `id_object` = '%s'",
                    id
                );

                Statement metatST = con.createStatement();
                ResultSet metaRS = metatST.executeQuery(cur_query);

                while(metaRS.next()){
                    
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

    public List<Post> GetPosts(){
        return new ArrayList<Post>();
    }


}
