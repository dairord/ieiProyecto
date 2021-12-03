package traductores;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileReader;
//import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.fasterxml.jackson.databind.SerializationFeature;

//import com.fasterxml.jackson.databind.*;
//import com.fasterxml.jackson.dataformat.csv.*;
public class CSVtoJSON {

	public static void main(String args[]) throws Exception {
    File output = new File("/home/dairor/eclipse-workspace/EuToJSON/src/JSON/CV.json");
	try (InputStream in = new FileInputStream("/home/dairor/eclipse-workspace/EuToJSON/src/NoJSON/CV.csv");) {
	    CSV csv = new CSV(true, ';', in);
	    List<String> fieldNames = null;
	    List<String> realFields = null;
	    if ( csv.hasNext() ) {
	    	fieldNames = new ArrayList<>(csv.next());
	    }
	    List<Map<String,String>> list = new ArrayList<>();
	    while (csv.hasNext()) {
	        List<String> x = csv.next();
	        Map<String,String> obj = new LinkedHashMap<>();
	        for (int i = 0 ; i < fieldNames.size() ; i++) {
	            obj.put(fieldNames.get(i), x.get(i));
	        }
	        list.add(obj);
	    }
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(output, list);
	}
	
	
	}
}
