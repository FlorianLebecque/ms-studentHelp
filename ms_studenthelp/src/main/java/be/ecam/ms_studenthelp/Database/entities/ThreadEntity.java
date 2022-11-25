package be.ecam.ms_studenthelp.Database.entities;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Object.ForumThread;
import be.ecam.ms_studenthelp.Object.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "threads")
public class ThreadEntity {
    @Id
    @Column(name = "id", unique = true)
    private String id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id")  // Foreign key
    private CategoryEntity category;

    @NonNull
    @Column(name = "answered")
    private boolean answered;

    @NonNull
    @Column(name = "date_posted")
    private LocalDateTime datePosted;

    @NonNull
    @Column(name = "date_modified")
    private LocalDateTime dateModified;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_post", referencedColumnName = "id")
    private PostEntity firstPost;

    @OneToMany(mappedBy="thread", cascade = CascadeType.ALL)
    private Set<TagEntity> tags;

    protected ThreadEntity() {};

    public ThreadEntity(
            @NonNull String id,
            @NonNull String title,
            @NonNull CategoryEntity category,
            boolean answered,
            @NonNull LocalDateTime datePosted,
            @NonNull LocalDateTime dateModified,
            @NonNull PostEntity firstPost,
            @NonNull Set<TagEntity> tags) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.answered = answered;
        this.datePosted = datePosted;
        this.dateModified = dateModified;
        this.firstPost = firstPost;
        this.tags = tags;
    }

    public ThreadEntity(
            @NonNull String title,
            @NonNull CategoryEntity category,
            @NonNull PostEntity firstPost) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.category = category;
        this.answered = false;
        this.datePosted = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.firstPost = firstPost;
        this.tags = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public @NotNull CategoryEntity getCategory() {
        return category;
    }

    public boolean getAnswered() {
        return answered;
    }

    @NonNull
    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    @NonNull
    public LocalDateTime getDateModified() {
        return dateModified;
    }

    public @NonNull PostEntity getFirstPost() {
        return firstPost;
    }

    @NonNull
    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
        this.dateModified = LocalDateTime.now();
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
        this.dateModified = LocalDateTime.now();
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
        this.dateModified = LocalDateTime.now();
    }

    public void setDatePosted(@NonNull LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public void setDateModified(@NonNull LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public void setFirstPost(@NonNull PostEntity firstPost) {
        this.firstPost = firstPost;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
        this.dateModified = LocalDateTime.now();
    }

    public void addTag(TagEntity tag) {
        this.tags.add(tag);
    }

    public IForumThread toForumThread() {
        Set<Tag> tags = this.tags
                .stream()
                .map(object -> new Tag(
                        object.getId(),
                        object.getTitle())
                ).collect(Collectors.toSet());

        return new ForumThread(
                id,
                title,
                answered,
                category.toCategory(),
                datePosted,
                dateModified,
                firstPost.toPost(),
                tags
        );
    }
}
