package be.ecam.ms_studenthelp.Database.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "reactions")
public class ReactionEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "value")
    private int value;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")  // Foreign key
    private AuthorEntity author;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")  // Foreign key
    private PostEntity post;

    protected ReactionEntity() {};

    public ReactionEntity(int value, @NonNull AuthorEntity author, @NonNull PostEntity post) {
        this.value = value;
        this.author = author;
        this.post = post;
    }

    public String getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    @NonNull
    public AuthorEntity getAuthor() {
        return author;
    }

    @NonNull
    public PostEntity getPost() {
        return post;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setAuthor(@NonNull AuthorEntity author) {
        this.author = author;
    }

    public void setPost(@NonNull PostEntity post) {
        this.post = post;
    }
}
