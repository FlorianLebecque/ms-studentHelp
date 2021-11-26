package be.ecam.ms_studenthelp.Object;

import java.util.Date;
import java.util.List;

import org.apache.juli.DateFormatCache;

import be.ecam.ms_studenthelp.utils.*;
//import jdk.internal.vm.annotation.ForceInline;
//import jdk.vm.ci.meta.Local;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Post {

    private String id;
    private LocalDateTime datePosted;

    private String authorId;
    private String content;
    private ForumThread forumThread;
    
    
    private List<LocalDateTime> dateModified = new ArrayList<LocalDateTime>();

    

    private Post parent;
    private List<Post> children = new ArrayList<Post>();

    private boolean modified = false;
    private boolean deleted = false;

    public Post(){
        id          =  GuidGenerator.GetNewUUIDString();
        datePosted  = LocalDateTime.now();
    }

    public Post(String _authorId, String _content, ForumThread _forumThread){
        id          =  GuidGenerator.GetNewUUIDString();
        datePosted  = LocalDateTime.now();

        SetAuthorId(_authorId);
        SetContent(_content);
        SetForumThread(_forumThread);

    }

    /// ---SETTERS--- ///
    public void SetAuthorId(String _authorId){
        authorId    = _authorId;
    }
    public void SetContent(String _content){
        content         = _content;
    }
    
    public void SetParent(Post _parent){
        parent      = _parent;
    }
    public void SetForumThread(ForumThread _forumThread){
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

    public Post getParent() {
        return parent;
    }

    public ForumThread getForumThread() {
        return forumThread;
    }

    public List<Post> getChildren() {
        return children;
    }

    public List<LocalDateTime> getDateModified() {
        return dateModified;
    }

    /* public String GetId(){
        return id;
    }
    public String GetContent(){
        return content;
    }
    public String GetAuthorId(){
        return authorId;
    }
    public LocalDateTime GetDatePosted(){
        return datePosted;
    }
    public List<LocalDateTime> GetDateModified(){
        return dateModified;
    }
    public ForumThread GetForumThread(){
        return forumThread;
    }
    public Post GetParent(){
        assert parent != null : "Parent is null";
        return parent;
    }
    public List<Post> GetChildren(){
        return children;
    }
    public boolean GetModified(){
        return modified;
    }
    public boolean GetDeleted(){
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
        Post reply  = new Post(_authorId,_content, this.forumThread);
        reply.SetParent(this);
        children.add(reply);
    }  

    
}
