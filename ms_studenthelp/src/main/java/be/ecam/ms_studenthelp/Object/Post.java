package be.ecam.ms_studenthelp.Object;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import org.jetbrains.annotations.Nullable;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class Post implements IPost{
    @NonNull private final String id;
    @NonNull private String content;
    private int upVotes = 0;
    private int downVotes = 0;
    @NonNull private final LocalDateTime datePosted;
    @NonNull private LocalDateTime dateModified;
    @NonNull private Author author;
    @Nullable private IPost parent;

    public Post(@NonNull String id,
                @NonNull String content,
                int upVotes,
                int downVotes,
                @NonNull LocalDateTime datePosted,
                @NonNull LocalDateTime dateModified,
                @NonNull Author author,
                @Nullable IPost parent) {
        this.id = id;
        this.content = content;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.datePosted = datePosted;
        this.dateModified = dateModified;
        this.author = author;
        this.parent = parent;
    }


    public Post(@NonNull String content,
                @NonNull Author author,
                @Nullable IPost parent) {
        this.id = UUID.randomUUID().toString();
        this.content = content;
        this.upVotes = 0;
        this.downVotes = 0;
        this.datePosted = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.author = author;
        this.parent = parent;
    }

    @Override
    @NonNull
    public String getId() {
        return id;
    }

    @Override
    @NonNull
    public Author getAuthor() {
        return author;
    }

    @Override
    public int getUpvotes() {
        return upVotes;
    }

    @Override
    public int getDownvotes() {
        return downVotes;
    }

    @Override
    @NonNull
    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    @Override
    @NonNull
    public LocalDateTime getDateModified() {
        return dateModified;
    }

    @Override
    @NonNull
    public String getContent() {
        return content;
    }

    @Override
    public IPost getParent() {
        return parent;
    }

    @Override
    public void setContent(@NonNull String content) {
        this.content = content;
        dateModified = LocalDateTime.now();
    }

    @Override
    public void setUpvotes(int upvotes) {
        this.upVotes = upvotes;
        dateModified = LocalDateTime.now();
    }

    @Override
    public void setDownvotes(int downvotes) {
        this.downVotes = downvotes;
        dateModified = LocalDateTime.now();
    }

    @Override
    public void setDateModified(@NonNull LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public void setParent(IPost parent) {
        this.parent = parent;
        dateModified = LocalDateTime.now();
    }

    @Override
    public void incrementUpvotes(int upVotes) {
        this.upVotes += upVotes;
        dateModified = LocalDateTime.now();
    }

    @Override
    public void incrementDownvotes(int downVotes) {
        this.downVotes += downVotes;
        dateModified = LocalDateTime.now();
    }

    @Override
    public void decrementUpvotes(int upVotes) {
        this.upVotes -= upVotes;
        dateModified = LocalDateTime.now();

        if (this.upVotes < 0) {
            this.upVotes = 0;
        }
    }

    @Override
    public void decrementDownvotes(int downVotes) {
        this.downVotes -= downVotes;
        dateModified = LocalDateTime.now();

        if (this.downVotes < 0) {
            this.downVotes = 0;
        }
    }
}
