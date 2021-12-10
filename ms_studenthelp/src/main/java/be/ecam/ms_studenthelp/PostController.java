package be.ecam.ms_studenthelp;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.Post;


@RestController
public class PostController {

    @GetMapping("/posts/{postId}")
    public IPost GetPostByPostId(@PathVariable("postId") String postId) {
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost(postId);
        return post;
    }

    @PatchMapping("/posts/{postId}")
    public IPost PatchPostByPostId(@PathVariable("postId") String postId, @RequestBody String body) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);
        String content = body_data.get("content").toString();
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost(postId);
        post.setContent(content);
        MsStudenthelpApplication.DatabaseManager.UpdatePost(post);
        return post;
    }

    @PutMapping("/posts/")
    public IPost ReplyPostByPostId(@RequestBody String body) {
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map<String,Object> body_data = springParser.parseMap(body);
        String authorId = body_data.get("authorId").toString();
        String content = body_data.get("content").toString();
        IPost post = new Post(authorId, content);
        MsStudenthelpApplication.DatabaseManager.CreatePost(post);
        return post;
    }

    @DeleteMapping("/posts/{postId}")
    public IPost deletePostByPostId(@PathVariable("postId") String postId) {
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost(postId);
        post.setContent("Deleted post");
        MsStudenthelpApplication.DatabaseManager.UpdatePost(post);
        return post;
    }

}
