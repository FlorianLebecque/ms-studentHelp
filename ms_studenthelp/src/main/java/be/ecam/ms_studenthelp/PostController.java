package be.ecam.ms_studenthelp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.Post;


@RestController
public class PostController {

    @GetMapping("/getPost")
    public IPost GetPost(){
        IPost post = new Post("L-A","qds");
        post.setContent("Coucou");
        return post;
    }

    @GetMapping("/helloMapping")
	public IPost index_ter() {
        //MsStudenthelpApplication.DatabaseManager.CreateForumThread(ft);
        IPost test = MsStudenthelpApplication.DatabaseManager.GetPost("uuid");
        MsStudenthelpApplication.DatabaseManager.UpdatePost(test);
        test.setContent("Hello Mapping!");
        //const myJSON = JSON.stringify(obj)
		return test;
	}

}
