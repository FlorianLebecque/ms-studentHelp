package be.ecam.ms_studenthelp.Database.entities;

import be.ecam.ms_studenthelp.Object.Tag;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "thread_id", referencedColumnName = "id")
    private ThreadEntity thread;

    protected TagEntity() {}

    public TagEntity(@NonNull String title, @NonNull ThreadEntity thread) {
        this.title = title;
        this.thread = thread;
    }

    @NonNull
    public long getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public ThreadEntity getThread() {
        return thread;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setThread(@NonNull ThreadEntity thread) {
        this.thread = thread;
    }

    public Tag toTag() {
        return new Tag(id, title);
    }
}
