package be.ecam.ms_studenthelp.Object;

import java.util.List;

import be.ecam.ms_studenthelp.Database.repositories.PostRepository;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.utils.*;
import ch.qos.logback.core.read.ListAppender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
//import jdk.internal.vm.annotation.ForceInline;
//import jdk.vm.ci.meta.Local;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.UUID;

public class Post implements IPost{
    @Autowired PostRepository postRepository;
    @NonNull private final String id;
    @NonNull private String content;
    private int upVotes = 0;
    private int downVotes = 0;
    @NonNull private final LocalDateTime datePosted;
    @NonNull private LocalDateTime dateModified;
    @NonNull private Author author;
    @Nullable private IPost parent;
    @NonNull private List<IPost> children;

    public Post(@NonNull String id,
                @NonNull String content,
                int upVotes,
                int downVotes,
                @NonNull LocalDateTime datePosted,
                @NonNull LocalDateTime dateModified,
                @NonNull Author author,
                @Nullable IPost parent,
                @NonNull List<IPost> children) {
        this.id = id;
        this.content = content;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.datePosted = datePosted;
        this.dateModified = dateModified;
        this.author = author;
        this.parent = parent;
        this.children = children;
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
        this.children = new ArrayList<>();
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
    @NonNull
    public List<IPost> getChildren() {
        return children;
    }

    @Override
    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @Override
    public void setUpvotes(int upvotes) {
        this.upVotes = upvotes;
    }

    @Override
    public void setDownvotes(int downvotes) {
        this.downVotes = downvotes;
    }

    @Override
    public void setDateModified(@NonNull LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public void setParent(IPost parent) {
        this.parent = parent;
    }

    @Override
    public void setChildren(@NonNull List<IPost> children) {
        this.children = children;
    }

    @Override
    public void incrementUpvotes(int upVotes) {
        this.upVotes += upVotes;
    }

    @Override
    public void incrementDownvotes(int downVotes) {
        this.downVotes += downVotes;
    }

    @Override
    public void decrementUpvotes(int upVotes) {
        this.upVotes -= upVotes;

        if (this.upVotes < 0) {
            this.upVotes = 0;
        }
    }

    @Override
    public void decrementDownvotes(int downVotes) {
        this.downVotes -= downVotes;

        if (this.downVotes < 0) {
            this.downVotes = 0;
        }
    }
}
