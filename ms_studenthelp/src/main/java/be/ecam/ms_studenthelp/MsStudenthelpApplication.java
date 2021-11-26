package be.ecam.ms_studenthelp;


import java.util.Arrays;


import org.springframework.boot.CommandLineRunner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

	

}
