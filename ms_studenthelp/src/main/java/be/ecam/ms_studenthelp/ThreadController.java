package be.ecam.ms_studenthelp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.ForumThread;
import be.ecam.ms_studenthelp.Object.Post;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
public class ThreadController {

    /**
     * POST /threads
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/post-threads
     */
    @PostMapping("/threads")
    public IForumThread PostThreads(@RequestBody String body){
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);

        // ForumThread info
        String title = (String) body_data.get("title");
        List<String> tags = (List<String>) body_data.get("tags");
        String category = (String) body_data.get("category");
        Boolean answered = (Boolean) body_data.get("answered");

        if (title == null || tags == null || category == null) {
            return null; // TODO: proper error handling, Bad Request
        }
        if (answered == null) {
            answered = false;
        }

        // child Post info
        Map<String,String> first_post = (Map<String,String>) body_data.get("first_post");
        if (first_post == null) {
            return null; // TODO: proper error handling, Bad Request
        }
        String content = first_post.get("content");

        if (content == null) {
            return null; // TODO: proper error handling, Bad Request
        }

        String authorID = "Someone"; // TODO : the author is the authenticated user


        IPost post = new Post(authorID, content);
        IForumThread thread = new ForumThread(title, authorID, category, post, answered);
        thread.AddTags(tags);

        MsStudenthelpApplication.DatabaseManager.CreatePost(post);
        MsStudenthelpApplication.DatabaseManager.CreateForumThread(thread);

        return thread;
    }

    /**
     * GET /threads/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/get-threads-threadId
     */
    @GetMapping("/threads/{threadId}")
    public IForumThread getThreadsThreadId(@PathVariable("threadId") String threadId) {
        IForumThread thread = MsStudenthelpApplication.DatabaseManager.GetForumThread(threadId);
        return thread;
    }

    /**
     * PATCH /threads/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/patch-threads-threadId
     */
    @PatchMapping("/threads/{threadId}")
    public IForumThread patchThreadsThreadId(@PathVariable("threadId") String threadId, @RequestBody String body) {
        IForumThread thread = MsStudenthelpApplication.DatabaseManager.GetForumThread(threadId);

        if (thread == null) {
            return null; // TODO: proper error handling, Not Found
        }

        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);

        // ForumThread info
        String title = (String) body_data.get("title");
        List<String> tags = (List<String>) body_data.get("tags");
        String category = (String) body_data.get("category");
        Boolean answered = (Boolean) body_data.get("answered");

        if (title != null) {
            thread.UpdateTitle(title);
        }
        if (tags != null) {
            thread.AddTags(tags);
        }
        if (category != null) {
            thread.UpdateCategory(category);
        }
        if (answered != null) {
            thread.UpdateAnswered(answered);
        }

        MsStudenthelpApplication.DatabaseManager.UpdateForumThread(thread);
        return thread;
    }

    /**
     * DELETE /threads/{threadId}
     * https://beta.bachelay.eu/ms-studentHelp/#/operations/delete-threads
     */
    @DeleteMapping("/threads/{threadId}")
    public IForumThread DeleteForumThreadTitle(@PathVariable("threadId") String threadId) {
        IForumThread thread = MsStudenthelpApplication.DatabaseManager.GetForumThread(threadId);
        if (thread == null) {
            return null; // Idempotency, if it doesn't exist, it is deleted
        }
        thread.UpdateTitle("Deleted");
        MsStudenthelpApplication.DatabaseManager.UpdateForumThread(thread);

        IPost post = thread.getChild();
        if (post == null) {
            return null; // Idempotency, if it doesn't exist, it is deleted
        }
        MsStudenthelpApplication.DatabaseManager.UpdatePost(post);

        return null;
    }

    /*
    @GetMapping("/thread/GetForumPages/{nbr_per_page}/{page_index}")
    public List<IForumThread> GetForumThreadPages(@PathVariable("nbr_per_page") int nbr_per_page, @PathVariable("page_index") int page_index) {
        List<IForumThread> ft_list = MsStudenthelpApplication.DatabaseManager.GetForumThreads(nbr_per_page,page_index);
        return ft_list;
    }
    */

}
