import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.json.XML;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
//import org.json.*;
import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class toXML {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		String xmlString =  null;    
		// Convertir Fichero XML -> Fichero Json con eco en la consola
		String xmlFile = "/home/dairor/eclipse-workspace/EuToJSON/src/biblioteques.xml";
		xmlString = new String(Files.readAllBytes(Paths.get(xmlFile)));
		JSONObject xmlJSONObj = null;
		xmlJSONObj = XML.toJSONObject(xmlString);
		String jsonFile = "/home/dairor/eclipse-workspace/EuToJSON/src/biblioteques.json";
		String jsonPrettyPrintString = null;
		try (FileWriter fileWriter = new FileWriter(jsonFile)){
		fileWriter.write(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
		jsonPrettyPrintString= xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		System.out.println(jsonPrettyPrintString);
		}

	}

}
