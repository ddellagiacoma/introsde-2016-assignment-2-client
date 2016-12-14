package requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class R6 {
	static String measure_id;
	static String measureType;

	static void sendR6xml(String id) throws Exception {
		//send R6 for xml for each measure types from measure_types
		for (int i = 0; i < R9.measure_types.size(); i++) {
			String url = Main.URL + "person/" + id + "/" + R9.measure_types.get(i);
			
			//new http request and set parameter
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/xml");

			int responseCode = con.getResponseCode();
			
			Main.outXML.println();
			Main.outXML.println("\nRequest #6: GET " + url + " Accept: application/xml");

			//get response
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//parse xml response
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new InputSource(new StringReader(response.toString())));
			Element rootElement = document.getDocumentElement();

			//if the response has content
			if (rootElement.getFirstChild() != null) {
				//store one measure_id and one measureType
				measureType = R9.measure_types.get(i);
				measure_id = rootElement.getElementsByTagName("idMeasureHistory").item(0).getTextContent().toString();
				//save result into log file
				Main.outXML.println("=> Result: OK");
				Main.outXML.println("=> HTTP Status: " + responseCode);
				Main.outXML.println(response);
			} else {
				Main.outXML.println("=> Result: ERROR");
				Main.outXML.println("=> HTTP Status: " + responseCode);
			}
		}
	}
	
	static void sendR6json(String id) throws Exception {
		//send R6 for json for each measure types from measure_types
		for (int i = 0; i < R9.measure_types.size(); i++) {
			String url = Main.URL + "person/" + id + "/" + R9.measure_types.get(i);

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Accept", "application/json");

			//get response code
			int responseCode = con.getResponseCode();
			
			Main.outJSON.println();
			Main.outJSON.println("\nRequest #6: GET " + url + " Accept: application/json");

			//get response
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONArray jArray = new JSONArray(response.toString());
			
			//if the response has content
			if (jArray.length() != 0) {
				Main.outJSON.println("=> Result: OK");
				Main.outJSON.println("=> HTTP Status: " + responseCode);
				Main.outJSON.println(response);
			} else {
				Main.outJSON.println("=> Result: ERROR");
				Main.outJSON.println("=> HTTP Status: " + responseCode);
			}
		}
	}
}