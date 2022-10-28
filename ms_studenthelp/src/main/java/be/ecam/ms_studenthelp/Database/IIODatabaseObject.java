package be.ecam.ms_studenthelp.Database;

import java.util.List;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Interfaces.IReaction;

public interface IIODatabaseObject{

    public boolean connect();
    public void disconnect();

    public boolean isConnected();

    /*
     *  I IO Database Object
     * 
     *  Create, read, update, delete for all Object (interface) 
     *      Naming convention:
     * 
     *          read   : Get+ObjectName+(String uuid);
     *                      +ObjectName+s(int max_per_page,int page_index)  -> should return a list
     *          Create : Create+ObjectName+(InterfaceName obj);
     *          Update : Update+ObjectName+(InterfaceName obj);
     *          Delete : Delete+ObjectName+(InterfaceName obj);
     * 
     *      There is no delete for most of the interface -> We need to keep the database integrity
     *      When we want to delete anything, the content is simply set to "[Deleted]"
     * 
     */
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
