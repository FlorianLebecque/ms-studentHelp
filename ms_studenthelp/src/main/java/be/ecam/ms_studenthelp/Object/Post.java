package be.ecam.ms_studenthelp.Object;

import java.util.Date;
import java.util.List;

import org.apache.juli.DateFormatCache;

import java.util.ArrayList;
import java.time.LocalDateTime;

public class Post {

    private String id;
    private String content;
    private String authorId;
    
    private LocalDateTime datePosted;
    private List<LocalDateTime> dateModified = new ArrayList<LocalDateTime>();

    private Post parent;
    private List<Post> children = new ArrayList<Post>();

    public Post(String _authorId){
        authorId    = _authorId;
        SetId();
        SetDate();
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void SetId(){
        id          =  "null";
    }

    public void SetContent(String _content){
        content     = _content;
    }
    public void SetDate(){
        datePosted = LocalDateTime.now();
    }
    public void UpdateDate(){

        dateModified.add(datePosted);
        datePosted = LocalDateTime.now();
    }

    /*
    public Post(String _id, String _content, String _author){
        id          = _id;
        content     = _content;
        authorId    = _author;
        datePosted  = LocalDateTime.now();
    }
    */

    public void ModifyContent(String _content){
        content = _content;
        UpdateDate();

    }

    public void DeleteChildren(){
        ModifyContent("--deleted--");
    }

    public void Reply(){
        Post reply = new Post("42");
        children.add(reply);
        System.out.println(children);

    }  

    
}
