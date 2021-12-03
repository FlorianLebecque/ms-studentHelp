package be.ecam.ms_studenthelp.Object;
//package be.ecam.ms_studenthelp.restservice;

import be.ecam.ms_studenthelp.Interfaces.IGreetings;

//Example with param

public class Greeting implements IGreetings{

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
