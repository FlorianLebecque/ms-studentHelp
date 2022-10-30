package be.ecam.ms_studenthelp.Interfaces;

import be.ecam.ms_studenthelp.Object.Category;
import be.ecam.ms_studenthelp.Object.Tag;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public interface IForumThread {
    @NonNull
    String getId();

    @NonNull
    String getTitle();

    boolean isAnswered();

    @NonNull
    Category getCategory();

    @NonNull
    LocalDateTime getDatePosted();

    @NonNull
    LocalDateTime getDateModified();

    IPost getFirstPost();

    @NonNull
    Set<Tag> getTags();

    void setTitle(@NonNull String title);
    void setAnswered(boolean answered);
    void setCategory(@NonNull Category category);
    void setDateModified(@NonNull LocalDateTime dateModified);
    void setFirstPost(IPost firstPost);
    void setTags(@NonNull Set<Tag> tags);

    @Override
    String toString();
}
