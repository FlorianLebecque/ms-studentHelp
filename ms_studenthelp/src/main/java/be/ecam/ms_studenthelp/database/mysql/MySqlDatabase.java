package be.ecam.ms_studenthelp.database.mysql;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import be.ecam.ms_studenthelp.Object.ForumThread;
import be.ecam.ms_studenthelp.Object.Post;
import be.ecam.ms_studenthelp.database.*;

public class MySqlDatabase implements IODatabaseObject {


    static Connection con = null;

    public boolean connect(){
        
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        }
        catch (Exception E)
        {
            System.err.println("Unable to load driver");
            E.printStackTrace();
        }

        String MySQLURL = "jdbc:mysql://remotemysql.com";
        String databseUserName = "nJWYJY1ijy";
        String databasePassword = "U7YpjttCs0";
        /*
        try {
            con = DriverManager.getConnection(MySQLURL, databseUserName, databasePassword);
            if (con != null) {
                System.out.println("Database connection is successful !!!!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //Class.forName("com.mysql.jdbc.Driver");
        try{
            String connectionUrl = "jdbc:mysql://remotemysql.com";
            Connection conn = DriverManager.getConnection(connectionUrl,databseUserName,databasePassword);
            ResultSet rs = conn.prepareStatement("show tables").executeQuery();
    
            while(rs.next()){
                String s = rs.getString(1);
                System.out.println(s);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        
        return false;
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
