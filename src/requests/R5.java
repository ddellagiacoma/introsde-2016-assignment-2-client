package requests;

import java.net.HttpURLConnection;
import java.net.URL;

public class R5 {
	
	static void sendR5xml() throws Exception {
		String url = Main.URL+"person/" + R4.xml_person_id;
		
		//delete the person just created
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("DELETE");
		con.getResponseCode();
		
		//then send R2 with the id of that person
		Integer result = sendR2("xml");
		
		Main.outXML.println();
		Main.outXML.println("\nRequest #5: DELETE " + url);

		//if the answer is 404 or 500, your result must be OK
		if (result == 404||result == 500) {

			Main.outXML.println("=> Result: OK");
			Main.outXML.println("=> HTTP Status: " + result);
		} else {
			Main.outXML.println("=> Result: ERROR");
			Main.outXML.println("=> HTTP Status: " + result);
		}
	}
	
	static void sendR5json() throws Exception {

		String url = Main.URL+"person/" + R4.json_person_id;
		
		//delete the person just created
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("DELETE");
		con.getResponseCode();
		
		//then send R2 with the id of that person
		Integer result = sendR2("json");
		
		Main.outJSON.println();
		Main.outJSON.println("\nRequest #5: DELETE " + url);

		//if the answer is 404 or 500, your result must be OK
		if (result == 404||result == 500) {

			Main.outJSON.println("=> Result: OK");
			Main.outJSON.println("=> HTTP Status: " + result);
		} else {
			Main.outJSON.println("=> Result: ERROR");
			Main.outJSON.println("=> HTTP Status: " + result);
		}
	}

	//check whether the person just deleted is still present
	static Integer sendR2(String type) throws Exception {
		URL obj = null;
		if (type.equals("xml")) {
			obj = new URL(Main.URL + "person/" + R4.xml_person_id);

		} else if (type.equals("json")) {
			obj = new URL(Main.URL + "person/" + R4.json_person_id);

		}
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		return responseCode;
	}
}
