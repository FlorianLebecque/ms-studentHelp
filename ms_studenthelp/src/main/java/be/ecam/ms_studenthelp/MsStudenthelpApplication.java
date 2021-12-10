package be.ecam.ms_studenthelp;





import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import be.ecam.ms_studenthelp.Database.IIODatabaseObject;
import be.ecam.ms_studenthelp.Database.mysql.MySqlDatabase;
import be.ecam.ms_studenthelp.Object.ForumThread;
import be.ecam.ms_studenthelp.Database.IIODatabaseObject;
import be.ecam.ms_studenthelp.Database.mysql.MySqlDatabase;
import be.ecam.ms_studenthelp.utils.GuidGenerator;

@SpringBootApplication
public class MsStudenthelpApplication {

	static IIODatabaseObject DatabaseManager;
	public static void main(String[] args) {

		DatabaseManager = new MySqlDatabase();

		if(DatabaseManager.isConnected()){
			SpringApplication.run(MsStudenthelpApplication.class, args);

		}
	}

}
