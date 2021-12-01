import java.io.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;
public class CSVtoJSONv2 {

	public static void main(String args[]) throws Exception {
    File output = new File("C:\\Users\\Fran\\Documents\\UNIVERSIDAD\\IEI\\ieiProyecto-master\\CV.json");
	try (InputStream in = new FileInputStream("C:\\Users\\Fran\\Documents\\UNIVERSIDAD\\IEI\\ieiProyecto-master\\CV.csv");) {
	    CSV csv = new CSV(true, ',', in);
	    List<String> fieldNames = null;
	    if ( csv.hasNext() ) fieldNames = new ArrayList<>(csv.next());
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
	    mapper.enable(SerializationFeature.INDENT_OUTPUT);
	    mapper.writeValue(output, list);
	}
	}
}
