package be.ecam.ms_studenthelp.Object;

import org.springframework.lang.NonNull;

public class Category {
    private final long id;

    @NonNull
    private final String title;

    public Category(long id, @NonNull String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }
}
