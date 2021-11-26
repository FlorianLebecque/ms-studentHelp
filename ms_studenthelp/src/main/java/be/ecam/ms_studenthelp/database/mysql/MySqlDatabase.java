package be.ecam.ms_studenthelp.database.mysql;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        String databseUserName = "";
        String databasePassword = "";

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
            //String query = String.format("INSERT INTO `mssh_object`(`id`, `type`, `datetime`, `author`) VALUES ('%s','%d','%s','%s')",);


            PreparedStatement ps = con.prepareStatement(""); 
            ResultSet rs = ps.executeQuery();




            /*
            while (rs.next()) {
                long id = rs.getLong("ID");
                String name = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");

                // do something with the extracted data...
            }
            */
            return 1;
        } catch (Exception e) {
            return -1;
        }


    }


    public ForumThread GetForumThread(String uuid){
        return new ForumThread("tqset", "id1", "test");
    }

    public List<ForumThread> GetForumThreads(){
        return new ArrayList<ForumThread>();
    }

    public Post GetPost(String uuid){
        return new Post();
    }

    public List<Post> GetPosts(){
        return new ArrayList<Post>();
    }


}
