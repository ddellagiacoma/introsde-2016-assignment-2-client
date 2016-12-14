package requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class R2 {

	//send R2 request for xml
	static void sendR2xml() throws Exception {
		String url = Main.URL+"person/" + R1.first_person_id;

		//new http request
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/xml");
		
		//get response code
		int responseCode = con.getResponseCode();
		
		//save into log file
		Main.outXML.println();
		Main.outXML.println("Request #2: GET " + url + " Accept: application/xml");

		//if response code is 200 or 202
		if (responseCode == 200 || responseCode == 202) {
			//save into log file
			Main.outXML.println("=> Result: OK");
			Main.outXML.println("=> HTTP Status: " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//save response
			Main.outXML.println(response);

		} else {
			Main.outXML.println("=> Result: ERROR");
			Main.outXML.println("=> HTTP Status: " + responseCode);
		}
	}
	
	//send R2 for json
	static void sendR2json() throws Exception {
		String url = Main.URL+"person/" + R1.first_person_id;
		
		//new http request
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		
		//get response code
		int responseCode = con.getResponseCode();
		
		//save into log file
		Main.outJSON.println();
		Main.outJSON.println("Request #2: GET " + url + " Accept: application/json");

		if (responseCode == 200 || responseCode == 202) {
			Main.outJSON.println("=> Result: OK");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//save response
			Main.outJSON.println(response);

		} else {
			Main.outJSON.println("=> Result: ERROR");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
		}
	}
	

	
}
