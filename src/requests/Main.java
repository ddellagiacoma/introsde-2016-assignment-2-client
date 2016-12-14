package requests;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {

	//server url of the student who worked with me
	public static final String URL = "http://localhost:5900/sdelab/";

	static PrintWriter outXML = null;
	static PrintWriter outJSON = null;

	public static void main(String[] args) throws Exception {

		//writers used to save the responses into log files
		outXML = new PrintWriter(new BufferedWriter(new FileWriter("client-server-xml.log", true)));
		outJSON = new PrintWriter(new BufferedWriter(new FileWriter("client-server-json.log", true)));

		//send all the requests
		Integer nPerson = R1.sendCount();
		R1.sendR1xml(nPerson);
		R1.sendR1json(nPerson);
		R2.sendR2xml();
		R2.sendR2json();
		R3.sendR3json();
		R3.sendR3xml();
		R4.sendR4json();
		R4.sendR4xml();
		R5.sendR5json();
		R5.sendR5xml();
		R9.sendR9xml();
		R9.sendR9json();
		R6.sendR6xml(R1.first_person_id);
		R6.sendR6xml(R1.last_person_id);
		R6.sendR6json(R1.first_person_id);
		R6.sendR6json(R1.last_person_id);
		R7.sendR7xml();
		R7.sendR7json();
		R8.sendR8xml();
		R8.sendR8json();

		//close xml and json writers
		outXML.close();
		outJSON.close();

		System.out.println("Requests completed");
	}
}