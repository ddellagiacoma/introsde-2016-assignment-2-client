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

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class R8 {
	
	static void sendR8xml() throws Exception {

		//send the request R6 and save count value 
		int count_value = countR6("xml");

		String url = Main.URL + "person/" + R1.first_person_id + "/" + R6.measureType;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/xml");
		con.setRequestProperty("Content-Type", "application/xml");

		//set request body and send it
		String str = "<healthMeasureHistory><timestamp>2011-12-09</timestamp><value>72</value></healthMeasureHistory>";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		//get response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//get response code
		int responseCode = con.getResponseCode();
		
		Main.outXML.println();
		Main.outXML.println("\nRequest #8: POST " + url + " Accept: application/xml Content-Type: application/xml");

		//follow up with another R6 as the first to check the new count value
		int new_count_value = countR6("xml");
		
		//if it is 1 measure more print OK, else print ERROR
		if (new_count_value == (count_value + 1)) {
			Main.outXML.println("=> Result: OK");
			Main.outXML.println("=> HTTP Status: " + responseCode);
			Main.outXML.println(response);
		} else {
			Main.outXML.println("=> Result: ERROR");
			Main.outXML.println("=> HTTP Status: " + responseCode);
		}
	}

	static void sendR8json() throws Exception {
		
		//send the request R6 and save count value 
		int count_value = countR6("json");
		
		String url = Main.URL + "person/" + R1.first_person_id + "/" + R6.measureType;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");

		//set request body and send it
		String str = "{\"value\": \"72\", \"timestamp\":\"2011-12-09 00:00:00\"}";
		byte[] outputInBytes = str.getBytes("UTF-8");
		OutputStream os = con.getOutputStream();
		os.write(outputInBytes);
		os.close();

		//get response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//get response code
		int responseCode = con.getResponseCode();
		
		Main.outJSON.println();
		Main.outJSON.println("\nRequest #8: POST " + url + " Accept: application/json Content-Type: application/json");

		//follow up with another R6 as the first to check the new count value
		int new_count_value = countR6("json");
		
		//if it is 1 measure more print OK, else print ERROR
		if (new_count_value == (count_value + 1)) {
			Main.outJSON.println("=> Result: OK");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
			Main.outJSON.println(response);
		} else {
			Main.outJSON.println("=> Result: ERROR");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
		}
	}

	static int countR6(String accept) throws Exception {

		String url = Main.URL + "person/" + R1.first_person_id + "/" + R6.measureType;

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/" + accept);
		con.getResponseCode();

		//get response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		//return the number of histories present
		if (accept.equals("xml")) {
			return countXML(response.toString());
		} else if (accept.equals("json")) {
			return countJSON(response.toString());
		}
		return 0;
	}

	//count how many histories are present for xml response
	static int countXML(String response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("idMeasureHistory");
		int count = 0;
		for (int i = 0; i < nodeList.getLength(); i++) {
			count++;
		}
		return count;
	}

	//count how many histories are present for json response
	static int countJSON(String response) throws ParserConfigurationException, SAXException, IOException {
		JSONArray jArray = new JSONArray(response.toString());
		return jArray.length();
	}
	
}
