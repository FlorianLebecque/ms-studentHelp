package be.ecam.ms_studenthelp.Database.entities;

import javax.persistence.*;

@Entity
@Table(name = "authors")
public class AuthorEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true)
    private String id;

    @OneToOne(mappedBy = "author")
    private PostEntity postEntity;

    protected AuthorEntity() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
