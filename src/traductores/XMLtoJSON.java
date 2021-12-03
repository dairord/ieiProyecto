package traductores;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.json.XML;

public class XMLtoJSON {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		String xmlString =  null;    
		// Convertir Fichero XML -> Fichero Json con eco en la consola
		String xmlFile = "/home/dairor/eclipse-workspace/EuToJSON/src/NoJSON/CAT.xml";
		xmlString = new String(Files.readAllBytes(Paths.get(xmlFile)));
		JSONObject xmlJSONObj = null;
		xmlJSONObj = XML.toJSONObject(xmlString);
		String jsonFile = "/home/dairor/eclipse-workspace/EuToJSON/src/JSON/CAT.json";
		String jsonPrettyPrintString = null;
		try (FileWriter fileWriter = new FileWriter(jsonFile)){
		fileWriter.write(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
		jsonPrettyPrintString= xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		System.out.println(jsonPrettyPrintString);
		}

	}

}
