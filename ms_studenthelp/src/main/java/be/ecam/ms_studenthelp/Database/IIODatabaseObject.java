package be.ecam.ms_studenthelp.Database;

import java.util.List;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Interfaces.IReaction;

public interface IIODatabaseObject{

    public boolean connect();
    public void disconnect();

    public boolean isConnected();

    public IForumThread GetForumThread(String uuid);
    public int CreateForumThread(IForumThread ft);
    public int UpdateForumThread(IForumThread ft);
    public List<IForumThread> GetForumThreads(int nbr_per_page,int page_index);

    public IPost GetPost(String uuid);
    public int CreatePost(IPost pt);
    public int UpdatePost(IPost pt);

    public IReaction GetReaction(IPost pt, String authorUuid);
    public List<IReaction> GetReactions(IPost pt);
    public IReaction CreateReaction(IReaction reaction);
    public IReaction UpdateReaction(IReaction reaction);
    public IReaction DeleteReaction(IReaction reaction);

}
