package be.ecam.ms_studenthelp.Interfaces;

import be.ecam.ms_studenthelp.Object.Author;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public interface IPost {
    @NonNull String getId();
    @NonNull Author getAuthor();
    int getUpvotes();
    int getDownvotes();
    @NonNull LocalDateTime getDatePosted();
    @NonNull LocalDateTime getDateModified();
    @NonNull String getContent();
    IPost getParent();

    void setContent(@NonNull String content);
    void setUpvotes(int upvotes);
    void setDownvotes(int downvotes);
    void setDateModified(@NonNull LocalDateTime dateModified);
    void setParent(IPost parent);

    void incrementUpvotes(int upvotes);
    void incrementDownvotes(int downvotes);
    void decrementUpvotes(int upvotes);
    void decrementDownvotes(int downvotes);
}
