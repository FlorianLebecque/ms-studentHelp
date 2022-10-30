package be.ecam.ms_studenthelp.Object;

import org.springframework.lang.NonNull;

public class Tag {
    private long id;
    private @NonNull String title;

    public Tag(long id, @NonNull String title) {
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

    public void setTitle(@NonNull String title) {
        this.title = title;
    }
}
