package be.ecam.ms_studenthelp.database;

import java.util.List;

import be.ecam.ms_studenthelp.Object.ForumThread;
import be.ecam.ms_studenthelp.Object.Post;

public interface IODatabaseObject{

    public boolean connect();
    public void disconnect();

    public ForumThread GetForumThread(String uuid);
    public List<ForumThread> GetForumThreads();

    public Post GetPost(String uuid);
    public List<Post> GetPosts();

}