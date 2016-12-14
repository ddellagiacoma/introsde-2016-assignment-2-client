package requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class R1 {

	static String first_person_id = "";
	static String last_person_id = "";
	
	//count how many people are present in the database
	static Integer sendCount() throws Exception {

		String url = Main.URL+"person/count";

		//http request
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.getResponseCode();

		//get the response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		return Integer.parseInt(response.toString());

	}

	//send R1 for xml
	static void sendR1xml(Integer nPerson) throws Exception {

		String url = Main.URL+"person";

		//new http connection and set the parameter
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/xml");

		//save the response code
		int responseCode = con.getResponseCode();
		
		Main.outXML.println("Request #1: GET " + url + " Accept: application/xml");

		//if more than 2 people the result is ok
		if (nPerson >= 2) {

			Main.outXML.println("=> Result: OK");
			Main.outXML.println("=> HTTP Status: " + responseCode);

			//get the response of the server
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//save the result on the log file
			Main.outXML.println(response);
			//explore the response and save first and last person id
			readXML(response.toString());
		} else {
			Main.outXML.println("=> Result: ERROR");
			Main.outXML.println("=> HTTP Status: " + responseCode);
		}
	}
	
	//send R1 for json
	static void sendR1json(Integer nPerson) throws Exception {

		String url = Main.URL+"person";
		
		//new http connection and set the parameters
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");
		
		//save response code
		int responseCode = con.getResponseCode();
		
		//save into log file 
		Main.outJSON.println("Request #1: GET " + url + " Accept: application/json");

		//if more than 2 people the result is ok
		if (nPerson >= 2) {

			Main.outJSON.println("=> Result: OK");
			Main.outJSON.println("=> HTTP Status: " + responseCode);

			//get the response
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
	
	//read xml response and save first and last person id
	static void readXML(String response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		Node first = rootElement.getFirstChild();
		Node last = rootElement.getLastChild();
		Element eElement = (Element) first;
		first_person_id = eElement.getElementsByTagName("idPerson").item(0).getTextContent().toString();
		Element eElement2 = (Element) last;
		last_person_id = eElement2.getElementsByTagName("idPerson").item(0).getTextContent().toString();
	}
	
}