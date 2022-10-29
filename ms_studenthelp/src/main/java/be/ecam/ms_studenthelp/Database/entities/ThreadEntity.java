package be.ecam.ms_studenthelp.Database.entities;

import org.hibernate.annotations.GenericGenerator;
import org.jetbrains.annotations.Nullable;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "threads")
public class ThreadEntity {
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    @NonNull
    @Column(name = "title")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    private CategoryEntity category;

    @NonNull
    @Column(name = "answered")
    private boolean answered;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "first_post", referencedColumnName = "id")
    private PostEntity firstPost;

    @OneToOne(mappedBy = "thread")
    private TagEntity tag;

    protected ThreadEntity() {};

    public String getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public boolean getAnswered() {
        return answered;
    }

    public @Nullable PostEntity getFirstPost() {
        return firstPost;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public void setFirstPost(@Nullable PostEntity firstPost) {
        this.firstPost = firstPost;
    }
}
