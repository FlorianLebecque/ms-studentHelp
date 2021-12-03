package be.ecam.ms_studenthelp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ecam.ms_studenthelp.Interfaces.IPost;
import be.ecam.ms_studenthelp.Object.Post;


@RestController
public class PostController {

    @GetMapping("/getPost")
    public IPost GetPost(){
        IPost post = new Post();
        post.setAuthorId("L-A");
        post.setContent("Coucou");
        return post;
    }

    @GetMapping("/helloMapping")
	public IPost index_ter() {

        //MsStudenthelpApplication.DatabaseManager.CreateForumThread(ft);



        IPost test = MsStudenthelpApplication.DatabaseManager.GetPost("uuid");

        MsStudenthelpApplication.DatabaseManager.UpdatePost((Post)test);
        

        test.setAuthorId("kamoulox");
        test.setContent("C'est la vie de chateau les blEUs?!!");
        //const myJSON = JSON.stringify(obj)
		return test;//"coucou";
	}

}
