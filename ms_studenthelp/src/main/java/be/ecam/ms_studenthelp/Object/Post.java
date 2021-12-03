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

    private String id;
    private LocalDateTime datePosted;

    private String authorId;
    private String content;
    private IForumThread forumThread;
    
    
    private List<LocalDateTime> dateModified = new ArrayList<LocalDateTime>();

    private IPost parent;
    private List<IPost> children = new ArrayList<IPost>();

    private boolean modified = false;
    private boolean deleted = false;

    public Post(){
        id          =  GuidGenerator.GetNewUUIDString();
        datePosted  = LocalDateTime.now();
    }

    public Post(String _authorId, String _content, IForumThread _forumThread){
        id          =  GuidGenerator.GetNewUUIDString();
        datePosted  = LocalDateTime.now();

        setAuthorId(_authorId);
        setContent(_content);
        setForumThread(_forumThread);
    }

    /// ---SETTERS--- ///
    public void setAuthorId(String _authorId){
        authorId    = _authorId;
    }
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
        return datePosted;
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

    public List<LocalDateTime> getDateModified() {
        return dateModified;
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

        dateModified.add(datePosted);
        datePosted  = LocalDateTime.now();
    }


    public void UpdateContent(String _content){
        content     = _content;
        modified = true;
        UpdateDate();
    }

    public void Delete(){
        deleted = true;
        UpdateContent("--deleted--");
    }

    public void Reply(String _authorId, String _content){
        IPost reply  = new Post(_authorId,_content, this.forumThread);
        reply.setParent(this);
        children.add(reply);
    }

    
}
