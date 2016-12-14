package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class R4 {
	static String xml_person_id;
	static Integer json_person_id;
	
	static void sendR4xml() throws Exception {

		String url = Main.URL+"person";

		//new http request and set parameters
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/xml");
		con.setRequestProperty("Content-Type", "application/xml");

		//set and send body of the request
		String str = "<person>" + "<birthdate>1945-01-01</birthdate>" + "<lastname>Norris</lastname>"
				+ "<HealthProfile>" + "<measureType>" + "<measureDefinition>" + "<idMeasureDef>1</idMeasureDef>"
				+ "<measureName>weight</measureName>" + "<measureType>double</measureType>" + "</measureDefinition>"
				+ "<value>78.9</value>" + "</measureType>" + "<measureType>" + "<measureDefinition>"
				+ "<idMeasureDef>2</idMeasureDef>" + "<measureName>height</measureName>"
				+ "<measureType>double</measureType>" + "</measureDefinition>" + "<value>172</value>" + "</measureType>"
				+ "</HealthProfile>" + "<firstname>Chuck</firstname>" + "</person>";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		//get the response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//get the response code
		int responseCode = con.getResponseCode();	
		
		Main.outXML.println();
		Main.outXML.println("\nRequest #4: POST " + url + " Accept: application/xml Content-Type: application/xml");

		//if the response code is 201 (200 or 202 are also applicable) with a person in the body who has an ID, the result is OK
		xml_person_id = readXML(response.toString());
		if ((responseCode == 200 || responseCode == 201|| responseCode == 202) && xml_person_id != null) {

			//save the result into xml log file
			Main.outXML.println("=> Result: OK");
			Main.outXML.println("=> HTTP Status: " + responseCode);
			Main.outXML.println(response);
		} else {
			Main.outXML.println("=> Result: ERROR");
			Main.outXML.println("=> HTTP Status: " + responseCode);
		}
	}
	
	static void sendR4json() throws Exception {

		String url = Main.URL+"person";
		
		//new http request and set parameters
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		
		//set and send body of the request
		String str ="{\"lastname\":\"Norris\",\"firstname\":\"Chuck\",\"birthdate\":\"1945-01-01\",\"measureType\": [{\"value\": \"78.9\",\"measureDefinition\": {\"idMeasureDef\": 1,\"measureName\": \"weight\",\"measureType\": \"double\"}},{\"value\": \"172\",\"measureDefinition\": {\"idMeasureDef\": 2,\"measureName\": \"height\",\"measureType\":\"double\"}}]}";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		//get response code
		int responseCode = con.getResponseCode();

		//get the response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		Main.outJSON.println();
		Main.outJSON.println("\nRequest #4: POST " + url + " Accept: application/json Content-Type: application/json");
		
		//if the response code is 201 (200 or 202 are also applicable) with a person in the body who has an ID, the result is OK
		JSONObject jObject = new JSONObject(response.toString());
		json_person_id = jObject.getInt("idPerson");
		if ((responseCode == 200 || responseCode == 201|| responseCode == 202) && json_person_id != null) {

			//save the result into json log file
			Main.outJSON.println("=> Result: OK");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
			Main.outJSON.println(response);
		} else {
			Main.outJSON.println("=> Result: ERROR");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
		}
	}

	static String readXML(String response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		return rootElement.getElementsByTagName("idPerson").item(0).getTextContent().toString();
	}
}
