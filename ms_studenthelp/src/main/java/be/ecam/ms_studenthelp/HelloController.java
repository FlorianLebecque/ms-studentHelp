package be.ecam.ms_studenthelp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import be.ecam.ms_studenthelp.Object.Post;
import be.ecam.ms_studenthelp.Object.Greeting;

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

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}


	@GetMapping("/get_Thread")
	public ForumThread get_Thread(){
		return new ForumThread( "Cours de geométrie" ,"Monsieur","Math");
	}

}
