package be.ecam.ms_studenthelp.Database.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class TagEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private String id;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "thread_id", referencedColumnName = "id")
    private ThreadEntity thread;

    protected TagEntity() {}

    public TagEntity(@NonNull String title, @NonNull ThreadEntity thread) {
        this.title = title;
        this.thread = thread;
    }

    @NonNull
    public String getId() {
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

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setThread(@NonNull ThreadEntity thread) {
        this.thread = thread;
    }
}
