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


    public LocalDateTime date;
    public LocalDateTime lastModif;

    
    public String content;
    

    public IPost parent;
    public List<IPost> children = new ArrayList<IPost>();

    public Post(String _authorId){
        id    = GuidGenerator.GetNewUUIDString();
        authorId = _authorId;

        date  = LocalDateTime.now();
        //lastModif = date;
    }

    public Post(String _authorId, String _content){
        id    = GuidGenerator.GetNewUUIDString();
        authorId = _authorId;
        date  = LocalDateTime.now();
        //lastModif = date;
        setContent(_content);
    }

    /// ---SETTERS--- ///

    public void setContent(String _content){
        content = _content;
    }
    
    public void setParent(IPost _parent){
        parent = _parent;
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
        IPost reply  = new Post(_authorId,_content);
        reply.setParent(this);
        children.add(reply);
    }

}
