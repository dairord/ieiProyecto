package wrappers;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.FileReader;
//import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class Euskadi {
	
	public static String nombre;
	public static String tipo;
	public static String direccion;
	public static String codigoPostal;
	public static double longitud;
	public static double latitud;
	public static String telefono;
	public static String email;
	public static String descripcion;
	public static String localidad_nombre;
	public static String localidad_codigo;
	public static String provincia_nombre;
	public static String provincia_codigo;
	
	public static Connection con = null;
	
	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		//Connection con = null;

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
        	Object obj = jsonParser.parse(new FileReader("/home/dairor/eclipse-workspace/EuToJSON/src/JSON/EUS.json"));
        	
			JSONArray listaBibliotecas = (JSONArray) obj;
			
			listaBibliotecas.forEach( biblioteca -> {
				
				try {
					
					uploadBiblioteca((JSONObject)biblioteca);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			
        }catch (Exception e) { System.out.println(e);}
	    
       
	    

	}
	
	public static void uploadBiblioteca(JSONObject bibliotecaJSON) throws SQLException {
		
		
		try {
		nombre = StringCutter.transformString((String) bibliotecaJSON.get("documentName"), '-');		
		tipo = "Publica";
		direccion =  (String) bibliotecaJSON.get("address") ;		
		codigoPostal = StringCutter.transformString((String) bibliotecaJSON.get("postalcode"), '.');
		longitud = Double.parseDouble(((String) bibliotecaJSON.get("lonwgs84")));
		latitud = Double.parseDouble((String) bibliotecaJSON.get("latwgs84"));
		telefono = (String) bibliotecaJSON.get("phone");
		email = (String) bibliotecaJSON.get("email");
		descripcion = StringCutter.transformString((String) bibliotecaJSON.get("documentDescription"), '-');
		localidad_nombre = StringCutter.stringSlicer((String) bibliotecaJSON.get("placename"), '-');
		localidad_codigo = codigoPostal;
		provincia_nombre = (String) bibliotecaJSON.get("municipality");
		provincia_codigo = ((String) bibliotecaJSON.get("postalcode")).substring(0, 2);
		
		}
		catch (Exception ex) { System.out.println(ex);}
		
		System.out.println(nombre +tipo +direccion +codigoPostal +longitud +latitud +telefono +email +descripcion
				+localidad_nombre +localidad_codigo +provincia_nombre +provincia_codigo);
		
		
		String query = "INSERT INTO biblioteca (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)"
				+ " VALUES ('"+nombre +"', '" +tipo +"', '" +direccion +"', '" +codigoPostal +"', '" +longitud +"', '" +latitud +"', '" +telefono +"', '" +email +"', '" +descripcion +"', '"
				+localidad_nombre +"', '" +localidad_codigo +"', '" +provincia_nombre +"', '" +provincia_codigo +"')";
		
		
		Statement st = con.createStatement();
		
		st.executeUpdate(query);
		
	}


}
