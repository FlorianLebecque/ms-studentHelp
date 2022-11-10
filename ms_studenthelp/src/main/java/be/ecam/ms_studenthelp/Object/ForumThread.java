package be.ecam.ms_studenthelp.Object;


import java.util.*;
import java.time.LocalDateTime;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

public class ForumThread implements IForumThread {
    @NonNull
    private final String id;

    @NonNull
    private String title;
    private boolean answered;

    @NonNull
    private Category category;

    @NonNull
    private final LocalDateTime datePosted;

    @NonNull
    private LocalDateTime dateModified;
    @NonNull private IPost firstPost;
    private Set<Tag> tags;

    //load a Thread
    public ForumThread(@NonNull String id,
                       @NonNull String title,
                       boolean answered,
                       @NonNull Category category,
                       @NonNull LocalDateTime datePosted,
                       @NonNull LocalDateTime dateModified,
                       @NonNull IPost firstPost,
                       Set<Tag> tags) {
        this.id = id;
        this.title = title;
        this.answered = answered;
        this.category = category;
        this.datePosted = datePosted;
        this.dateModified = dateModified;
        this.firstPost = firstPost;
        this.tags = tags;
    }

    public ForumThread(@NonNull String title,
                       @NonNull Category category,
                       @NonNull IPost firstPost) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.answered = false;
        this.category = category;
        this.datePosted = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.firstPost = firstPost;
        this.tags = new HashSet<>();
    }

    @Override
    @NonNull
    public String getId() {
        return id;
    }

    @Override
    @NonNull
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isAnswered() {
        return answered;
    }

    @Override
    @NonNull
    public Category getCategory() {
        return category;
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
    @NotNull
    public IPost getFirstPost() {
        return firstPost;
    }

    @Override
    public @NotNull Set<Tag> getTags() {
        return tags;
    }

    @Override
    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Override
    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    @Override
    public void setCategory(@NonNull Category category) {
        this.category = category;
    }

    @Override
    public void setDateModified(@NonNull LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    @Override
    public void setFirstPost(@NotNull IPost firstPost) {
        this.firstPost = firstPost;
    }

    @Override
    public void setTags(@NotNull Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Thread(" +
                "ID: " + id + "\n" +
                "Title: " + title + "\n" +
                "Answered: " + answered + "\n" +
                "Category: " + category.getTitle() + "\n" +
                "Date posted: " + datePosted + "\n" +
                "Date modified: " + dateModified + "\n" +
                "First post: " + firstPost.getContent() + "\n" +
                "Tags: " + tags + "\n" +
                ")";
    }
}
