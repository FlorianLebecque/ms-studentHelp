package be.ecam.ms_studenthelp.Database.mysql.MySqlSerializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import be.ecam.ms_studenthelp.Interfaces.*;
import be.ecam.ms_studenthelp.Object.*;


public class ReactionCRUD {
    
    private Connection con;

    public ReactionCRUD(Connection con_){
        con = con_;
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
