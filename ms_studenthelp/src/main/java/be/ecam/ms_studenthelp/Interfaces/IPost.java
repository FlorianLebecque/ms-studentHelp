package be.ecam.ms_studenthelp.Interfaces;

import java.time.LocalDateTime;
import java.util.List;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;

public interface IPost {
    //public Post();

    //public Post(String _authorId, String _content, ForumThread _forumThread);

    /// ---SETTERS--- ///
    public void setAuthorId(String _authorId);
    public void setContent(String _content);
    
    public void setParent(IPost _parent);
    public void setForumThread(IForumThread _forumThread);
    /// ------------- ///


    /// ---GETTERS--- ///

    public String getContent();

    public String getId();

    public String getAuthorId();

    public LocalDateTime getDatePosted();

    public IPost getParent();

    public IForumThread getForumThread();

    public List<IPost> getChildren();

    public List<LocalDateTime> getDateModified();

    /* public String GetId(){
>>>>>>> b7f79efe06cc1b706bcc9a3237b516f05b55a690
        return id;
    }
    public String getContent(){
        return content;
    }
    public String getAuthorId(){
        return authorId;
    }
    public LocalDateTime getDatePosted(){
        return datePosted;
    }
    public List<LocalDateTime> getDateModified(){
        return dateModified;
    }
    public ForumThread getForumThread(){
        return forumThread;
    }
    public Post getParent(){
        assert parent != null : "Parent is null";
        return parent;
    }
    public List<Post> getChildren(){
        return children;
    }
    public boolean getModified(){
        return modified;
    }
    public boolean getDeleted(){
        return deleted;
    } */


    /// ------------- ///

    public void UpdateDate();


    public void UpdateContent(String _content);

    public void Delete();

    public void Reply(String _authorId, String _content);
}
