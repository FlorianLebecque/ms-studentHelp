package be.ecam.ms_studenthelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	public static void main(String[] args) {
		SpringApplication.run(MsStudenthelpApplication.class, args);
	}

}
