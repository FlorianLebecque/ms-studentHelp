package be.ecam.ms_studenthelp.Database.entities;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.Post;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @Column(name = "id", unique = true)
    private String id;

    @NonNull
    @Column(name = "content")
    private String content;

    @NonNull
    @Column(name = "upvotes")
    private int upVotes;

    @NonNull
    @Column(name = "downvotes")
    private int downVotes;

    @NonNull
    @Column(name = "date_posted")
    private LocalDateTime datePosted;

    @NonNull
    @Column(name = "date_modified")
    private LocalDateTime dateModified;

    @Nullable
    @ManyToOne/*(cascade = CascadeType.ALL)*/
    @JoinColumn(name = "parent", referencedColumnName = "id")  // Foreign key
    private PostEntity parent;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @NonNull
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<ReactionEntity> reactions;

    @NonNull
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<PostEntity> children;

    protected PostEntity() {}

    public PostEntity(@NonNull String content,
                      @NonNull int upVotes,
                      @NonNull int downVotes,
                      @NonNull LocalDateTime datePosted,
                      @NonNull LocalDateTime dateModified,
                      @Nullable PostEntity parent,
                      @NonNull AuthorEntity author,
                      @NonNull Set<ReactionEntity> reactions) {
        id = UUID.randomUUID().toString();
        this.content = content;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.datePosted = datePosted;
        this.dateModified = dateModified;
        this.parent = parent;
        this.author = author;
        this.reactions = reactions;
    }

    public PostEntity(@NonNull String content,
                      @NonNull AuthorEntity author) {
        id = UUID.randomUUID().toString();
        this.content = content;
        this.upVotes = 0;
        this.downVotes = 0;
        this.datePosted = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();;
        this.parent = null;
        this.author = author;
        this.reactions = new HashSet<>();
    }

    public String getId() {
        return id;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    @NonNull
    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    @NonNull
    public LocalDateTime getDateModified() {
        return dateModified;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    @Nullable
    public PostEntity getParent() {
        return parent;
    }

    @NonNull
    public Set<ReactionEntity> getReactions() {
        return reactions;
    }

    @NonNull
    public Set<PostEntity> getChildren() {
        return children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
        this.dateModified = LocalDateTime.now();
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
        this.dateModified = LocalDateTime.now();

        if (this.upVotes < 0) {
            this.upVotes = 0;
        }
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
        this.dateModified = LocalDateTime.now();

        if (this.downVotes < 0) {
            this.downVotes = 0;
        }
    }

    public void setDatePosted(@NonNull LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    public void setDateModified(@NonNull LocalDateTime dateModified) {
        this.dateModified = dateModified;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public void setParent(@Nullable PostEntity parent) {
        this.parent = parent;
        this.dateModified = LocalDateTime.now();
    }

    public void setReactions(@NonNull Set<ReactionEntity> reactions) {
        this.reactions = reactions;
        this.dateModified = LocalDateTime.now();
    }

    public IPost toPost() {
        IPost parent = this.parent != null ? this.parent.toPost() : null;

        return new Post(
                id,
                content,
                upVotes,
                downVotes,
                datePosted,
                dateModified,
                author.toAuthor(),
                parent);
    }
}
