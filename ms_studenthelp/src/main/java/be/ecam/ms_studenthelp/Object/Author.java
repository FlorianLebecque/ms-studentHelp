package be.ecam.ms_studenthelp.Object;

import org.springframework.lang.NonNull;

public class Author {
    @NonNull
    private final String id;

    public Author(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getId() {
        return id;
    }
}
