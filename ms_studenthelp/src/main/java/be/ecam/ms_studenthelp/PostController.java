package be.ecam.ms_studenthelp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.Post;
import be.ecam.ms_studenthelp.Interfaces.IForumThread;
import be.ecam.ms_studenthelp.utils.*;
import be.ecam.ms_studenthelp.Database.IIODatabaseObject;
import be.ecam.ms_studenthelp.Object.Reaction;
import be.ecam.ms_studenthelp.Interfaces.IReaction;


@RestController
public class PostController {

    //TEST

    @GetMapping("/getPost")
    public IPost GetPost(){
        IPost post = new Post("L-A");
        post.setContent("Coucou");
        return post;
    }

    @GetMapping("/helloMapping")
	public IPost index_ter() {

        //MsStudenthelpApplication.DatabaseManager.CreateForumThread(ft);

        IPost test = MsStudenthelpApplication.DatabaseManager.GetPost("uuid");

        MsStudenthelpApplication.DatabaseManager.UpdatePost(test);

        test.setContent("C'est la vie de chateau les blEUs?!!");
        //const myJSON = JSON.stringify(obj)
		return test;//"coucou";
	}

    // GOOD

    @GetMapping("/posts/{postId}")
    public IPost GetPostByPostId(@PathVariable("postId") String postId) {
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost(postId);
        return post;
    }

    @PatchMapping("/posts/{postId}")
    public IPost PatchPostByPostId(@PathVariable("postId") String postId) {
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost(postId);

        //Récupérer nouvelles infos, les changer dans la DB et renvoyer le nouveau post

        return post;
    }

    @PutMapping("/posts/{postId}")
    public IPost ReplyPostByPostId(@PathVariable("postId") String postId) {
        IPost post = new Post("");
        // Récupérer infos et créer un nouveau post dans la DB
        return post;
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity deletePostByPostId(@PathVariable("postId") String postId) {
        // Delete le post dans la DB
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
