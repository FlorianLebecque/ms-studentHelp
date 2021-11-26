package be.ecam.ms_studenthelp;

import be.ecam.ms_studenthelp.Object.ForumThread;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public String get_Thread(){
		ForumThread Thread1 = new ForumThread( "Titre1" ,"String authorId_","String category_");
		String stringToParse = Thread1.GetJson();
		return stringToParse;
	}

}
