package be.ecam.ms_studenthelp.Object;

import java.util.List;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.utils.*;
//import jdk.internal.vm.annotation.ForceInline;
//import jdk.vm.ci.meta.Local;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Post implements IPost{
    /**
     * The Post object represents a post inside a parent thread. 
     * He can have answers that will be referenced in his 'children' list.
     * 2 ways to construct a Post object:
     *  CREATE: when you create a new Post
     *      @param {String} _authorId
     *      @param {String} _content
     *      Other @parameters must be defined by the setters
     *  LOAD: when you load an existing Post from the database
     *      @param {String} id_
     *      @param {String} authorId_
     *      @param {Stirg} content_
     *      @param {LocalDateTime} date_
     *      @param {LocalDateTime} LastModif_
     *      @param {IPost} parent_
     */

    private final String id;
    private final String authorId;
    public LocalDateTime date;
    public LocalDateTime lastModif;
    public String content;
    public IPost parent;
    public List<IPost> children = new ArrayList<IPost>();

    //create
    public Post(String _authorId,String _content){
        id    = GuidGenerator.GetNewUUIDString();
        authorId = _authorId;
        date  = LocalDateTime.now();
        lastModif = date;
        content = _content;
    }

    //load
    public Post(String id_,String authorId_,String content_ ,LocalDateTime date_,LocalDateTime lastModif_ ,IPost parent_){
        id = id_;
        authorId = authorId_;
        date = date_;
        lastModif = date_;
        content = content_;
        parent = parent_;
    }

    /// ---SETTERS--- ///

    public void setContent(String _content){
        content = _content;
    }
    
    public void setParent(IPost _parent){
        parent = _parent;
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

    public LocalDateTime getLastModif() {
        return lastModif;
    }
    /// ------------- ///

    public void UpdateContent(String _content){
        content     = _content;
        UpdateDate();
    }


    public void UpdateDate(){

        lastModif  = LocalDateTime.now();
    }


    public void Delete(){
        UpdateContent("--deleted--");
        UpdateDate();
    }
    
    //To create a reply: create a new Post before using function Reply(IPost reply) which defines the new Post as beeing the reply
    public void Reply(IPost reply){
        reply.setParent(this);
        children.add(reply);
    }

}
