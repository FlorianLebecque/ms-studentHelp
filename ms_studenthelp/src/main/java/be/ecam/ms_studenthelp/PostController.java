package be.ecam.ms_studenthelp;

import be.ecam.ms_studenthelp.Interfaces.IReaction;
import be.ecam.ms_studenthelp.Object.Reaction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.Post;

import java.util.ArrayList;


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
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost("postId");
        return post;
    }

    @PatchMapping("/posts/{postId}")
    public IPost PatchPostByPostId(@PathVariable("postId") String postId) {
        IPost post = MsStudenthelpApplication.DatabaseManager.GetPost("postId");

        //Récupérer nouvelles infos, les changer dans la DB et renvoyer le nouveau post

        return post;
    }

    /*@PutMapping("/posts/{postId}")
    public IPost PutPostByPostId(@PathVariable("postId") String postId) {
        // Récupérer infos et créer un nouveau post dans la DB
        return post;
    }


    @DeleteMapping("/posts/{postId}")
    public IPost deletePostByPostId(@PathVariable("postId") String postId) {
        // Delete le post dans la DB
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    */

}
