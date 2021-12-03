package be.ecam.ms_studenthelp.Object;

import java.util.List;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.utils.*;
//import jdk.internal.vm.annotation.ForceInline;
//import jdk.vm.ci.meta.Local;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Post implements IPost{

    private final String id;
    private final String authorId;


    private LocalDateTime date;
    private LocalDateTime lastModif;

    
    private String content;
    private IForumThread forumThread;
    
    

    private IPost parent;
    private List<IPost> children = new ArrayList<IPost>();

    public Post(String _authorId){
        id    = GuidGenerator.GetNewUUIDString();
        authorId = _authorId;

        date  = LocalDateTime.now();
    }

    public Post(String _authorId, String _content, IForumThread _forumThread){
        id    = GuidGenerator.GetNewUUIDString();
        authorId = _authorId;
        date  = LocalDateTime.now();

        setContent(_content);
        setForumThread(_forumThread);
    }

    /// ---SETTERS--- ///
    public void setContent(String _content){
        content         = _content;
    }
    
    public void setParent(IPost _parent){
        parent      = _parent;
    }
    public void setForumThread(IForumThread _forumThread){
        forumThread = _forumThread;
    }
    /// ------------- ///


    /// ---GETTERS--- ///

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public LocalDateTime getDatePosted() {
        return date;
    }

    public IPost getParent() {
        return parent;
    }

    public IForumThread getForumThread() {
        return forumThread;
    }

    public List<IPost> getChildren() {
        return children;
    }

    public LocalDateTime getDateModified() {
        return lastModif;
    }

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

    public void UpdateDate(){

        lastModif  = LocalDateTime.now();
    }


    public void UpdateContent(String _content){
        content     = _content;
        UpdateDate();
    }

    public void Delete(){
        UpdateContent("--deleted--");
        UpdateDate();
    }

    public void Reply(String _authorId, String _content){
        IPost reply  = new Post(_authorId,_content, this.forumThread);
        reply.setParent(this);
        children.add(reply);
    }

    
}
