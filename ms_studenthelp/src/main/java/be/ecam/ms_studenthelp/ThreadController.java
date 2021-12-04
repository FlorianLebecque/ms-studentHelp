package be.ecam.ms_studenthelp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("/thread/UpdateTitle/{threadId}/{newTitle}")
    public IForumThread UpdateForumThreadTitle(@PathVariable("threadId") String threadId, @PathVariable("newTitle") String newTitle) {
        IForumThread thread = MsStudenthelpApplication.DatabaseManager.GetForumThread(threadId);
        thread.UpdateTitle(newTitle);
        MsStudenthelpApplication.DatabaseManager.UpdateForumThread(thread);
        return thread;
    }

    @GetMapping("/thread/UpdateCategory/{threadId}/{newCategory}")
    public IForumThread UpdateForumThreadCategory(@PathVariable("threadId") String threadId, @PathVariable("newCategory") String newCategory) {
        IForumThread thread = MsStudenthelpApplication.DatabaseManager.GetForumThread(threadId);
        thread.UpdateCategory(newCategory);
        MsStudenthelpApplication.DatabaseManager.UpdateForumThread(thread);
        return thread;
    }

    @GetMapping("/thread/GetForumPages/{nbr_per_page}/{page_index}")
    public List<IForumThread> GetForumThreadPages(@PathVariable("nbr_per_page") int nbr_per_page, @PathVariable("page_index") int page_index) {
        List<IForumThread> ft_list = MsStudenthelpApplication.DatabaseManager.GetForumThreads(nbr_per_page,page_index);
        return ft_list;
    }

}