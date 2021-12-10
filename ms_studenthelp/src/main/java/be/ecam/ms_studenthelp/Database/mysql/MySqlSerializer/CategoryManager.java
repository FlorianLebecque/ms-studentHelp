package be.ecam.ms_studenthelp.Database.mysql.MySqlSerializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.ecam.ms_studenthelp.Interfaces.*;
import be.ecam.ms_studenthelp.Object.*;


public class CategoryManager {

    private Connection con;

    public CategoryManager(Connection con_){
        con = con_;
    }

    public List<String> GetCategories(){
        try {
            String cur_query = "SELECT title FROM `mssh_category` ORDER BY id";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(cur_query);

            ArrayList<String> categories = new ArrayList<String>();
            while(rs.next()) {
                String category = rs.getString("title");
                categories.add(category);
            }

            return categories;
        } catch (Exception e) {
            return null;
        }
    }
}

