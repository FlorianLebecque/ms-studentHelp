package be.ecam.ms_studenthelp.Object;


import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;    

import be.ecam.ms_studenthelp.utils.GuidGenerator;

public class ForumThread {

    private final String id;
    private String title;
    private List<String> tags;
    private final String authorId;
    private LocalDateTime date;
    private String category;
    private boolean answered;
    private List<Post> children;
    private List<LocalDateTime> modification;
    
    //load
    public ForumThread(String id_,String title_,List<String> tags_,String authorId_,LocalDateTime date_,String category_,boolean answered_,List<Post> children_){
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
        children  = new ArrayList<Post>();
    }

    public void Reply(Post reply){
        children.add(reply);
    }

    public void UpdateTitle(String title_){
        title = title_;
        
        if(modification == null){
            modification = new ArrayList<LocalDateTime>();
        }

        //TODO copy dates

        //modification.add(new LocalDateTime(date));
        date = LocalDateTime.now();
    }

    public void Delete(){
        children.clear();
    }

}
