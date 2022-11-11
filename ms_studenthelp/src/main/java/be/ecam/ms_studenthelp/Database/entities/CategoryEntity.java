package be.ecam.ms_studenthelp.Database.entities;

import be.ecam.ms_studenthelp.Object.Category;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private long id;

    @NonNull
    @Column(name = "title")
    private String title;

    protected CategoryEntity() {}

    public CategoryEntity(@NonNull String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("Categories[id=%d, title='%s']", id, title);
    }

    public Category toCategory() {
        return new Category(id, title);
    }
}
