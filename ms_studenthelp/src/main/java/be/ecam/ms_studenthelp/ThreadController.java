package be.ecam.ms_studenthelp;

import be.ecam.ms_studenthelp.Object.ForumThread;
import be.ecam.ms_studenthelp.Object.Post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadController{

    @GetMapping("/get_Thread")
    public ForumThread get_Thread(){
        return new ForumThread( "Cours de geométrie" ,"Monsieur","Math");}

}