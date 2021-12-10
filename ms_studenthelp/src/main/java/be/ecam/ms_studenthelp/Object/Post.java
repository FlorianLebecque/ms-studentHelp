package be.ecam.ms_studenthelp.Object;

import java.util.List;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.utils.*;
//import jdk.internal.vm.annotation.ForceInline;
//import jdk.vm.ci.meta.Local;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Post implements IPost{

    private final String id;
    private final String authorId;


    public LocalDateTime date;
    public LocalDateTime lastModif;

    
    public String content;
    

    public IPost parent;
    public List<IPost> children = new ArrayList<IPost>();

    public Post(String _authorId,String content_){
        id    = GuidGenerator.GetNewUUIDString();
        authorId = _authorId;

        date  = LocalDateTime.now();

        content = content_;

    }

    public Post(String id_,String authorId_,String content_ ,LocalDateTime date_,LocalDateTime lastModif_ ,IPost parent_){
        id = id_;
        authorId = authorId_;

        date = date_;
        lastModif = lastModif_;

        content = content_;

        parent = parent_;
    }


    /// ---SETTERS--- ///
    public void setContent(String _content){
        content         = _content;
    }
    
    public void setParent(IPost _parent){
        parent      = _parent;
    }

    public void setChildren(List<IPost> children_){
        children = children_;
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

    public void Reply(IPost reply){
        reply.setParent(this);
        children.add(reply);
    }

    
}
