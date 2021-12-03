package be.ecam.ms_studenthelp.Database;

import java.util.List;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.ForumThread;
import be.ecam.ms_studenthelp.Object.Post;

public interface IIODatabaseObject{

    public boolean connect();
    public void disconnect();


    public ForumThread GetForumThread(String uuid);
    public List<ForumThread> GetForumThreads();
    public int CreateForumThread(IForumThread ft);
    public int UpdateForumThread(IForumThread ft);


    public Post GetPost(String uuid);
    public int CreatePost(IPost pt);
    public int UpdatePost(IPost pt);
    public List<Post> GetPosts();

}