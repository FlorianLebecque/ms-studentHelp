package be.ecam.ms_studenthelp;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus; //TO DO : manage HTTP responses
import org.springframework.http.ResponseEntity; //TO DO : manage HTTP responses
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.Post;

@RestController
public class PostController {

    /**
     *  ENDPOINT GET post by postId
     *
     *  @param postId Id of the post to get
     *  @return and {@link IPost}
     */
    @GetMapping("/posts/{postId}")
    public IPost GetPostByPostId(@PathVariable("postId") String postId) {

        // Get targeted post in DB
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost(postId);

        return post;
    }


    /**
     * ENDPOINT PATCH to modify content of a post
     *
     * @param postId Id of the post to modify
     * @param body objet post in JSON or just a JSON like {"content": "also works"}
     * @return and {@link IPost}
     */
    @PatchMapping("/posts/{postId}")
    public IPost PatchPostByPostId(@PathVariable("postId") String postId, @RequestBody String body) {

        // Format the body in objet body_data to get attributes
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);
        String content = body_data.get("content").toString();

        // Update content's post in DB
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost(postId);
        post.setContent(content);
        MsStudenthelpApplication.DatabaseManager.UpdatePost(post);

        return post;
    }

    /**
     *  method to reply to a post
     *
     * @param postId Id of an existing post
     * @param body  The reply post with necessary fields to create a post
     * @return and {@link IPost}
     */
    @PutMapping("/posts/{postId}/answers")
    public IPost ReplyPostByPostId(@PathVariable("postId") String postId, @RequestBody String body) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);
        String authorId = body_data.get("authorId").toString();
        String content = body_data.get("content").toString();
        IPost post = new Post(authorId, content);
        IPost postToReply = MsStudenthelpApplication.DatabaseManager.GetPost(postId);
        postToReply.Reply(post);
        MsStudenthelpApplication.DatabaseManager.CreatePost(post);
        return post;
    }

    /**
     * Delete a post
     *
     * @param postId
     * @return
     */
    @DeleteMapping("/posts/{postId}")
    public IPost deletePostByPostId(@PathVariable("postId") String postId) {
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost(postId);
        post.setContent("Deleted post");
        MsStudenthelpApplication.DatabaseManager.UpdatePost(post);
        return post;
    }
}
