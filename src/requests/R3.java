package requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class R3 {

	static void sendR3xml() throws Exception {

		String url = Main.URL+"person/" + R1.first_person_id;
		
		//new http connection and set the parameters
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("PUT");
		con.setRequestProperty("Accept", "application/xml");
		con.setRequestProperty("Content-Type", "application/xml");

		//send the body of the request
		String str = "<person><firstname>Daniele</firstname><lastname>Norris</lastname><birthdate>1945-01-01</birthdate></person>";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		//get response code
		int responseCode = con.getResponseCode();
		
		Main.outXML.println();
		Main.outXML.println("\nRequest #3: PUT " + url + " Accept: application/xml Content-Type: application/xml");

		//get response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//get the firstname of the person in the response
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response.toString())));
		Element rootElement = document.getDocumentElement();
		String result = rootElement.getElementsByTagName("firstname").item(0).getTextContent().toString();
		
		//if the response has the name changed, the result is OK
		if (result.equals("Daniele")) {
			Main.outXML.println("=> Result: OK");
			Main.outXML.println("=> HTTP Status: " + responseCode);
			Main.outXML.println(response);

		} else {
			Main.outXML.println("=> Result: ERROR");
			Main.outXML.println("=> HTTP Status: " + responseCode);
		}
	}	

	static void sendR3json() throws Exception {

		String url = Main.URL+"person/" + R1.first_person_id;
		
		//new http connection and set the parameters
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("PUT");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");

		//send the body of the request
		String str = "{\"firstname\":\"Daniele\",\"lastname\":\"Norris\",\"birthdate\":\"1945-01-01\"}";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		//get response code
		int responseCode = con.getResponseCode();
				
		Main.outJSON.println();
		Main.outJSON.println("\nRequest #3: PUT " + url + " Accept: application/json Content-Type: application/json");

		//get the response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//get the firstname of the person in the response
		JSONObject jObject = new JSONObject(response.toString());
		String result = jObject.getString("firstname");
		
		//if the response has the name changed, the result is OK
		if (result.equals("Daniele")) {
			Main.outJSON.println("=> Result: OK");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
			Main.outJSON.println(response);
		} else {
			Main.outJSON.println("=> Result: ERROR");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
		}
	}
}