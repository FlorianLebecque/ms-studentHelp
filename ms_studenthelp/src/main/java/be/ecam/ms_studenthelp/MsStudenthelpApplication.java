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

	/*
	 * 	Entry point of the micro service
	 * 
	 * 		Project Tree : 
	 * 			../ms_studenthelp:
	 * 				Database				: all IIODatabaseObject implementation
	 * 					mysql				: mysql implementation
	 * 						MySqlSerializer : mysql serializer ( convert all object into the database format)
	 * 				Interfaces				: object interfaces
	 * 				Object					: object implementation 
	 * 				Path					: all endpoints for the API
	 * 				Utils					: utilitary class
	 * 
	 * 		DatabaseManager is an interface wich let us interact with the database
	 * 			- For the moment, it's an MySqlDatabase (implementation for MySql)
	 * 			- the connection is done in the constructor
	 * 
	 * 		When the micro service is launched, the connection to the database is started, when done
	 * 		Springboot start
	 */


	static IIODatabaseObject DatabaseManager;
	public static void main(String[] args) {

		DatabaseManager = new MySqlDatabase();

		if(DatabaseManager.isConnected()){
			SpringApplication.run(MsStudenthelpApplication.class, args);

		}
	}

}
