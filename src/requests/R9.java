package requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class R9 {
	
	static ArrayList<String> measure_types = new ArrayList<String>();

	static void sendR9xml() throws Exception {

		String url = Main.URL+"measureTypes";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/xml");

		//get response code
		int responseCode = con.getResponseCode();
		
		Main.outXML.println();
		Main.outXML.println("\nRequest #9: GET " + url + " Accept: application/xml");

		//get response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//read xml response and save all measureTypes into array measure_types
		readXML(response.toString());
		
		//if response contains more than 2 measureTypes the result is OK, else is ERROR
		if (measure_types.size() > 2) {
			Main.outXML.println("=> Result: OK");
			Main.outXML.println("=> HTTP Status: " + responseCode);
			Main.outXML.println(response);
		} else {
			Main.outXML.println("=> Result: ERROR");
			Main.outXML.println("=> HTTP Status: " + responseCode);
		}
	}
	
	static void sendR9json() throws Exception {

		String url = Main.URL+"measureTypes";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Accept", "application/json");

		//get response code
		int responseCode = con.getResponseCode();
		
		Main.outJSON.println();
		Main.outJSON.println("\nRequest #9: GET " + url + " Accept: application/json");

		//get response
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//if response contains more than 2 measureTypes the result is OK, else is ERROR
		if (measure_types.size() > 2) {
			Main.outJSON.println("=> Result: OK");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
			Main.outJSON.println(response);
		} else {
			Main.outJSON.println("=> Result: ERROR");
			Main.outJSON.println("=> HTTP Status: " + responseCode);
		}
	}
	
	//read xml response and save all measureTypes into array measure_types
	static void readXML(String response) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(new StringReader(response)));
		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getElementsByTagName("measureName");
		for (int i = 0; i < nodeList.getLength(); i++) {
			measure_types.add(nodeList.item(i).getTextContent().toString());
		}
	}
}
