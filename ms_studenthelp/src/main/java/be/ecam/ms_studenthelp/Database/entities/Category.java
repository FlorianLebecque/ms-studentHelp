package be.ecam.ms_studenthelp.Database.entities;

import javax.persistence.*;

@Entity
@Table(name="mssh_category")
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;

    protected Category() {}

    public Category(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("Categories[id=%d, title='%s']", id, title);
    }
}
