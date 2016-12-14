package requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class R7 {
	
	//send R7 for the stored measure_id and measureType
	static void sendR7xml() throws Exception {
		String url = Main.URL+"person/" + R1.first_person_id+"/"+R6.measureType+"/"+R6.measure_id;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/xml");
		
		//get response code
		int responseCode = con.getResponseCode();
		
		Main.outXML.println();
		Main.outXML.println("\nRequest #7: GET " + url + " Accept: application/xml");

		//if the response code is 200, result is OK, else is ERROR
		if (responseCode == 200) {
			Main.outXML.println("=> Result: OK");
			Main.outXML.println("=> HTTP Status: " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			Main.outXML.println(response);

		} else {
			Main.outXML.println("=> Result: ERROR");
			Main.outXML.println("=> HTTP Status: " + responseCode);
		}
	}
	
	//send R7json for the stored measure_id and measureType
	static void sendR7json() throws Exception {
		String url = Main.URL+"person/" + R1.first_person_id+"/"+R6.measureType+"/"+R6.measure_id;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		
		//get response code
		int responseCode = con.getResponseCode();
		
		Main.outJSON.println();
		Main.outJSON.println("\nRequest #7: GET " + url + " Accept: application/json");

		//if the response code is 200, result is OK, else is ERROR
		if (responseCode == 200) {
			Main.outJSON.println("=> Result: OK");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			Main.outJSON.println(response);

		} else {
			Main.outJSON.println("=> Result: ERROR");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
		}
	}
}