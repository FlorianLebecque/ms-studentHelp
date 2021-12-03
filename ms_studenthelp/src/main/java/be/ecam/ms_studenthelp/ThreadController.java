package be.ecam.ms_studenthelp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Object.ForumThread;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class ThreadController {

    @GetMapping("/CreateThread")
    public IForumThread CreateThreadByDefault(){
        IForumThread thread = new ForumThread("Un tres bon titre","Quelqu1","Une catégorie banale");
        MsStudenthelpApplication.DatabaseManager.CreateForumThread(thread);
        return thread;
    }

    @GetMapping("/createThread/{threadTitre}/{AutheurID}/{NomDeCatégorie}")
    //createThread/BeauTitre/Sarah/cathegory1
    public IForumThread CreateThread(@PathVariable("threadTitre") String threadTitre,
                                            @PathVariable("AutheurID") String AutheurID,
                                            @PathVariable("NomDeCatégorie") String NomDeCatégorie){
        IForumThread thread = new ForumThread(threadTitre, AutheurID,NomDeCatégorie);
        MsStudenthelpApplication.DatabaseManager.CreateForumThread(thread);
        return thread;
    }

    @GetMapping("/thread/{threadId}")
    //test id : 11815501-d8d1-4c05-a0a6-59c699064772
    public IForumThread GetThreadByThreadId(@PathVariable("threadId") String threadId) {
        IForumThread thread = MsStudenthelpApplication.DatabaseManager.GetForumThread(threadId);
        System.out.println(thread);
        return thread;
    }

    @GetMapping("/getThread")
    public IForumThread GetEasyThread(){
        IForumThread thread = new ForumThread("Le meilleur titre du monde","Quelquun","Une catégorie banale");
        return thread;
    }

    /*@GetMapping("/helloMapping")
    public IForumThread index_ter() {

        //MsStudenthelpApplication.DatabaseManager.CreateForumThread(ft);



        IForumThread test = MsStudenthelpApplication.DatabaseManager.GetThread("uuid");

        MsStudenthelpApplication.DatabaseManager.UpdateThread(test);


        test.setContent("C'est la vie de chateau les blEUs?!!");
        //const myJSON = JSON.stringify(obj)
        return test;//"coucou";
    }*/

}