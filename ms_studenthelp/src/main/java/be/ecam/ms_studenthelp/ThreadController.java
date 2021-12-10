package be.ecam.ms_studenthelp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Object.ForumThread;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


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

    @PostMapping("/createThreadPOST") //Creat a Thread usig POST and a body
    public IForumThread CreateThreadPOST(@RequestBody String body){
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);
        String threadTitre = (String) body_data.get("title");
        ArrayList<String> tags = (ArrayList<String>) body_data.get("tags");
        String NomDeCatégorie = (String) body_data.get("category");
        String AutheurID = "Someone"; //To be set : the author is the authenticated user
        IForumThread thread = new ForumThread(threadTitre, AutheurID,NomDeCatégorie);
        thread.AddTags(tags);
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

    @GetMapping("/thread/Delete/{threadId}")
    public IForumThread DeleteForumThreadTitle(@PathVariable("threadId") String threadId) {
        IForumThread thread = MsStudenthelpApplication.DatabaseManager.GetForumThread(threadId);
        thread.UpdateTitle("Deleted");
        MsStudenthelpApplication.DatabaseManager.UpdateForumThread(thread);
        return thread;
    }

    @GetMapping("/thread/GetForumPages/{nbr_per_page}/{page_index}")
    public List<IForumThread> GetForumThreadPages(@PathVariable("nbr_per_page") int nbr_per_page, @PathVariable("page_index") int page_index) {
        List<IForumThread> ft_list = MsStudenthelpApplication.DatabaseManager.GetForumThreads(nbr_per_page,page_index);
        return ft_list;
    }

}