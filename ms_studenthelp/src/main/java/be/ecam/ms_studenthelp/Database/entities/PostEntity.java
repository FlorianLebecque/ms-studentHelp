package be.ecam.ms_studenthelp.Database.entities;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Date;

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
    private Date datePosted;

    @NonNull
    @Column(name = "date_modified")
    private Date dateModified;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "parent", referencedColumnName = "id")  // Foreign key
    private PostEntity parent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private AuthorEntity author;

    protected PostEntity() {}

    public PostEntity(@NonNull String content, @NonNull int upVotes, @NonNull int downVotes, @NonNull Date datePosted, @NonNull Date dateModified, @NonNull AuthorEntity author) {
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
    public Date getDatePosted() {
        return datePosted;
    }

    @NonNull
    public Date getDateModified() {
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

    public void setDatePosted(@NonNull Date datePosted) {
        this.datePosted = datePosted;
    }

    public void setDateModified(@NonNull Date dateModified) {
        this.dateModified = dateModified;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public void setParent(@Nullable PostEntity parent) {
        this.parent = parent;
    }
}
