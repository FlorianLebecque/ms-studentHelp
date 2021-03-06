package be.ecam.ms_studenthelp.Interfaces;

import java.time.LocalDateTime;
import java.util.List;

public interface IPost {
    //public Post(String _authorId);

    //public Post(String _authorId, String _content, ForumThread _forumThread);

    /// ---SETTERS--- ///
    public void setContent(String _content);
    
    public void setParent(IPost _parent);

    public void setChildren(List<IPost> children_);

    /// ------------- ///


    /// ---GETTERS--- ///

    public String getContent();

    public String getId();

    public String getAuthorId();

    public LocalDateTime getDatePosted();

    public IPost getParent();

    public List<IPost> getChildren();

    public LocalDateTime getLastModif();

    /// ------------- ///

    public void UpdateDate();

    public void UpdateContent(String _content);

    public void Delete();

    public void Reply(IPost reply);
}
