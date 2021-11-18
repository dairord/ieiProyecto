import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class toJSON {
	
	public static String nombre;
	public static String tipo;
	public static String direccion;
	public static int codigoPostal;
	public static double longitud;
	public static double latitud;
	public static int telefono;
	public static String email;
	public static String descripcion;
	public static String localidad_nombre;
	public static int localidad_codigo;
	public static String provincia_nombre;
	public static int provincia_codigo;

	public static void main(String[] args) throws ClassNotFoundException {
		Connection con = null;

	    String url = "jdbc:mysql://localhost:3306/IEIDB";
	    String username = "root";
	    String password = "root";

	    try {
	      Class.forName("com.mysql.jdbc.Driver");
	      con = DriverManager.getConnection(url, username, password);

	      System.out.println("Connected!");

	    } catch (SQLException ex) {
	        throw new Error("Error ", ex);
	    }
	    
	    
	    
	    JSONParser jsonParser = new JSONParser();
        
        try {
        	Object obj = jsonParser.parse(new FileReader("/home/dairor/eclipse-workspace/EuToJSON/src/bibliotecas.json"));
        	
        	//JSONObject jsonObject = (JSONObject) obj;
        	 
			// A JSON array. JSONObject supports java.util.List interface.
			JSONArray listaBibliotecas = (JSONArray) obj;
			
			listaBibliotecas.forEach( biblioteca -> uploadBiblioteca((JSONObject)biblioteca));
			//fuploadBiblioteca(listaBibliotecas);
			
        }catch (Exception e) { System.out.println(e);}
	    
       
	    

	}
	
	public static void uploadBiblioteca(JSONObject bibliotecaJSON) {
		
		nombre = (String) bibliotecaJSON.get("documentName");
		
		System.out.println(nombre);
		
		
		
		
	}

}
