package wrappers;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Catalunya {

	public static int j = 1;
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
        	
        	Object obj = jsonParser.parse(new FileReader("/home/dairor/eclipse-workspace/EuToJSON/src/JSON/CAT.json"));
        	
        	JSONObject jsonObject = (JSONObject) obj;
        	obj = jsonObject.get("response");
        	jsonObject = (JSONObject) obj;
        	obj = jsonObject.get("row");
			JSONArray listaBibliotecas = (JSONArray) obj;
			listaBibliotecas.forEach( biblioteca -> {
				
				try {
					System.out.println(biblioteca.toString());
					uploadBiblioteca((JSONObject)biblioteca);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			
        }catch (Exception e) { System.out.println(e);}
	    
       
	    

	}
	//public static int j = 0;
	
	public static void uploadBiblioteca(JSONObject bibliotecaJSON) throws SQLException {
		
		
		try {
		nombre = StringCutter.transformString( bibliotecaJSON.get("nom").toString(), '.');
		nombre = StringCutter.transformString(nombre, "'");
		direccion =  StringCutter.transformString( bibliotecaJSON.get("via").toString(), ',') ;
		direccion = StringCutter.transformString(direccion, '-');
		direccion = StringCutter.transformString(direccion, "'");
		codigoPostal =  bibliotecaJSON.get("cpostal").toString();
		longitud = Double.parseDouble( bibliotecaJSON.get("longitud").toString());
		latitud = Double.parseDouble( bibliotecaJSON.get("latitud").toString());
		
		try {
		telefono =  bibliotecaJSON.get("telefon1").toString();
		}catch (Exception ex) {telefono = "";}
		
		email = bibliotecaJSON.get("email").toString();
		
		try {
			descripcion = bibliotecaJSON.get("propietats").toString();
		//descripcion = StringCutter.transformString( bibliotecaJSON.get("propietats").toString(), '|');
		} catch(Exception ex){ descripcion = "";}
		
		localidad_nombre =  StringCutter.transformString(bibliotecaJSON.get("poblacio").toString(), "'");
		localidad_codigo = bibliotecaJSON.get("codi_municipi").toString();
		provincia_codigo =  bibliotecaJSON.get("cpostal").toString().substring(0, 2);
		provincia_nombre =  StringCutter.nameProvincia(Integer.parseInt(provincia_codigo));
		
		if(descripcion.contains("Privada")) {
			tipo = "Privada";
		}else {tipo = "Pública";}
		}
		catch (Exception ex) {System.out.println(ex);};
		
		
		System.out.println(j +nombre + tipo + direccion + codigoPostal + longitud + latitud + telefono + email
				+ descripcion + localidad_nombre + localidad_codigo + provincia_nombre + provincia_codigo);

		j++;
		
		String query = "INSERT INTO bibliotecas (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)"
				+ " VALUES ('"+nombre +"', '" +tipo +"', '" +direccion +"', '" +codigoPostal +"', '" +longitud +"', '" +latitud +"', '" +telefono +"', '" +email +"', '" +descripcion +"', '"
				+localidad_nombre +"', '" +localidad_codigo +"', '" +provincia_codigo +"')";
		
		String queryLocalidades = "INSERT INTO localidades (localidad_nombre, localidad_codigo)"
				+ " VALUES ('"+localidad_nombre +"', '" +localidad_codigo +"')";
		
		String queryProvincias = "INSERT INTO provincias (provincia_nombre, provincia_codigo)"
				+ " VALUES ('" +provincia_nombre +"', '" +provincia_codigo +"')";
		
		
		Statement st = con.createStatement();
		
		try {
			st.executeUpdate(queryLocalidades);
		}catch(Exception ex) {
			System.out.println(ex);
		}
		
		try {
			st.executeUpdate(queryProvincias);
		}catch(Exception ex) {
			System.out.println(ex);
		}
		try {
			st.executeUpdate(query);
		} catch (Exception ex) { 
			descripcion = StringCutter.transformString(( (JSONObject) bibliotecaJSON.get("propietats")).get("content").toString(), "'");
			descripcion = StringCutter.transformString(descripcion, '"');
			query = "INSERT INTO bibliotecas (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)"
					+ " VALUES ('"+nombre +"', '" +tipo +"', '" +direccion +"', '" +codigoPostal +"', '" +longitud +"', '" +latitud +"', '" +telefono +"', '" +email +"', '" +descripcion +"', '"
					+localidad_nombre +"', '" +localidad_codigo +"', '" +provincia_nombre +"', '" +provincia_codigo +"')";
		st.executeUpdate(query);
		}
		
		System.out.println("Fin");
		
	}
	
	
	
	

}
