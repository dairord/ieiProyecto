package conexiones;

import java.io.IOException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class GeocodeLocation {

	public static String[] getLocation(String location) throws IOException, InterruptedException {
		
		String[] res = new String[2];

		ObjectMapper mapper = new ObjectMapper();
		Geocoder geocoder = new Geocoder();
		String response = geocoder.GeocodeSync(location);
		JsonNode responseJsonNode = mapper.readTree(response);
		//System.out.println(response);
		JsonNode items = responseJsonNode.get("items");

		for (JsonNode item : items) {
			// JsonNode address = item.get("address");
			// String label = address.get("label").asText();
			 JsonNode position = item.get("position");

			res[0] = position.get("lat").asText();
			res[1] = position.get("lng").asText();
			//System.out.println(label + " is located at " + lat + "," + lng + ".");

		}
		
		return res;
	}
}