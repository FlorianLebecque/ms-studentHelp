package be.ecam.ms_studenthelp;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import be.ecam.ms_studenthelp.database.IODatabaseObject;
import be.ecam.ms_studenthelp.database.mysql.MySqlDatabase;

@SpringBootApplication
public class MsStudenthelpApplication {

	static IODatabaseObject DatabaseManager;
	public static void main(String[] args) {

		DatabaseManager = new MySqlDatabase();

		if(DatabaseManager.connect()){
			SpringApplication.run(MsStudenthelpApplication.class, args);
		};


		
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

}
