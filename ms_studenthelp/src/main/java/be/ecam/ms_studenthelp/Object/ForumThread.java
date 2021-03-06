package be.ecam.ms_studenthelp.Object;


import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.utils.GuidGenerator;

public class ForumThread implements IForumThread {

    private final String id;
    private final String authorId;

    private LocalDateTime date;
    private LocalDateTime lastModif;


    private String title;
    private String category;
    private List<String> tags;
    private boolean answered;

    private IPost child;


    //load a Thread
    public ForumThread(String id_,String authorId_,String title_ ,String category_,List<String> tags_,LocalDateTime date_,LocalDateTime lastModif_,Boolean answered_,IPost child_){
        id       = id_;
        authorId = authorId_;

        date = date_;
        lastModif = lastModif_;

        title = title_;
        category = category_;
        tags = tags_;
        answered = answered_;

        child = child_;
    }

    //create a Thread
    public ForumThread(String title_,String authorId_,String category_,IPost child_,boolean answered_){
        id       = GuidGenerator.GetNewUUIDString();
        authorId = authorId_;
        title    = title_;
        tags     = new ArrayList<String>();
        date     = LocalDateTime.now();
        category = category_;
        answered = answered;
        child    = child_;
    }


    public void Reply(IPost reply){
        child = reply;
    }

    public void UpdateTitle(String title_){
        title = title_;

        lastModif = LocalDateTime.now();
    }

    public void UpdateCategory(String category_){
        category = category_;

        lastModif = LocalDateTime.now();
    }

    public void UpdateAnswered(boolean answered_) {
        answered = answered_;
    }

    public void Delete(){
        child.Delete();
    }
    public void AddTags(String tag){
        tags.add(tag);
    }
    public void AddTags(List<String> tag){
        for(String n : tag)
            tags.add(n);
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

    public IPost getChild() {
        return child;
    }

    public List<String> getTags() {
        return tags;
    }

    public LocalDateTime getModification() {
        return lastModif;
    }

    public boolean getAnswer(){
        return answered;
    }

}
