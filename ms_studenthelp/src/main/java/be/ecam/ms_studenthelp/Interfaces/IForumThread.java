package be.ecam.ms_studenthelp.Interfaces;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

public interface IForumThread {


    //public ForumThread(String id_,String title_,List<String> tags_,String authorId_,LocalDateTime date_,String category_,boolean answered_,List<Post> children_);

    //create
    //public ForumThread(String title_,String authorId_,String category_);

    public void UpdateTitle(String title_);
    public void UpdateCategory(String category_);

    public void Delete();
    public void AddTags(ArrayList<String> tag);
    public void AddTags(String tag);

    @Override
    public String toString();

    public String getAuthorId();

    public String getId();

    public String getCategory();

    public String getTitle();

    public LocalDateTime getDate();

    public IPost getChild();

    public List<String> getTags();

    public LocalDateTime getModification();
    
    public boolean getAnswer();
}
