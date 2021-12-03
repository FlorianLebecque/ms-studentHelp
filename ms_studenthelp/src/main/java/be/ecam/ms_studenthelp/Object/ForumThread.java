package be.ecam.ms_studenthelp.Object;


import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.utils.GuidGenerator;

public class ForumThread implements IForumThread {

    private final String id;
    private String title;
    private List<String> tags;
    private final String authorId;
    private LocalDateTime date;
    private String category;
    private boolean answered;
    private List<IPost> children;
    private List<LocalDateTime> modification;

    //load
    public ForumThread(String id_,String title_,List<String> tags_,String authorId_,LocalDateTime date_,String category_,boolean answered_,List<IPost> children_){
        id       = id_;
        title    = title_;
        authorId = authorId_;
        date     = date_;
        category = category_;
        answered = answered_;
        children  = children_;
    }

    //create
    public ForumThread(String title_,String authorId_,String category_){
        id       = GuidGenerator.GetNewUUIDString();
        title    = title_;
        tags     = new ArrayList<String>();
        authorId = authorId_;
        date     = LocalDateTime.now();
        category = category_;
        answered = false;
        children  = new ArrayList<IPost>();
    }

    public void Reply(IPost reply){
        children.add(reply);
    }

    public void UpdateTitle(String title_){
        title = title_;

        if(modification == null){
            modification = new ArrayList<LocalDateTime>();
        }
        date = LocalDateTime.now();
        modification.add(date);
    }

    public void Delete(){
        children.clear();
    }

    @Override
    public String toString() {
        return "Thread{" +
                "id=" + id +
                ", title=" + title +
                ", tags=" + tags +
                ", authorId=" + authorId +
                ", date=" + date +
                ", category=" + category +
                ", answered=" + answered +
                '}';
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<IPost> getChildren() {
        return children;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<LocalDateTime> getModification() {
        return modification;
    }
    
    public boolean getAnswer(){
        return answered;
    }

}
