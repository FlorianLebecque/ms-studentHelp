package be.ecam.ms_studenthelp.Database.entities;

import be.ecam.ms_studenthelp.Object.Author;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "authors")
public class AuthorEntity {
    @Id
    // @GeneratedValue(generator="system-uuid")
    // @GenericGenerator(name="id", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    protected AuthorEntity() {
        id = UUID.randomUUID().toString();
    }

    public AuthorEntity(@NonNull String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Author toAuthor() {
        return new Author(id);
    }
}
