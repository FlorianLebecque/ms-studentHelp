package be.ecam.ms_studenthelp.Object;
//package be.ecam.ms_studenthelp.restservice;

//Example with param

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
