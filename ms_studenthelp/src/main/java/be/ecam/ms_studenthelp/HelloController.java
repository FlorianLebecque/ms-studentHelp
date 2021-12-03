package be.ecam.ms_studenthelp;

import be.ecam.ms_studenthelp.Object.ForumThread;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HelloController {

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@GetMapping("/helloworld")
	public String index_bis() {



		return "HELLO SPRINGBOOTS!!!";

	}

	@GetMapping("/get_Thread")
	public ForumThread get_Thread(){

        //MsStudenthelpApplication.DatabaseManager.CreateForumThread(new ForumThread( "Cours de geométrie" ,"Monsieur","Math"));


		return new ForumThread( "Cours de geométrie" ,"Monsieur","Math");
	}

}
