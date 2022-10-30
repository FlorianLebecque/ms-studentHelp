package be.ecam.ms_studenthelp.Database.entities;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.Post;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
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
    @ManyToOne
    @JoinColumn(name = "parent", referencedColumnName = "id")  // Foreign key
    private PostEntity parent;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    protected PostEntity() {}

    public PostEntity(@NonNull String content,
                      @NonNull int upVotes,
                      @NonNull int downVotes,
                      @NonNull LocalDateTime datePosted,
                      @NonNull LocalDateTime dateModified,
                      @NonNull AuthorEntity author) {
        id = UUID.randomUUID().toString();
        this.content = content;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.datePosted = datePosted;
        this.dateModified = dateModified;
        this.author = author;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
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
                parent,
                new ArrayList<>()
                );
    }
}
