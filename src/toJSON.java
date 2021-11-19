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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class toJSON {
	
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
	    
/*Statement st = con.createStatement();

String query = "INSERT INTO biblioteca VALUES (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, "
		+ "descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo";*/



// note that i'm leaving "date_created" out of this insert statement

		
		//st.executeQuery(query);
	   
        try {
        	Object obj = jsonParser.parse(new FileReader("/home/dairor/eclipse-workspace/EuToJSON/src/bibliotecas.json"));
        	
        	//JSONObject jsonObject = (JSONObject) obj;
        	 
			// A JSON array. JSONObject supports java.util.List interface.
			JSONArray listaBibliotecas = (JSONArray) obj;
			
			listaBibliotecas.forEach( biblioteca -> {
				
				try {
					
					uploadBiblioteca((JSONObject)biblioteca);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			//fuploadBiblioteca(listaBibliotecas);
			
        }catch (Exception e) { System.out.println(e);}
	    
       
	    

	}
	
	public static void uploadBiblioteca(JSONObject bibliotecaJSON) throws SQLException {
		
		
		try {
		nombre = transformString((String) bibliotecaJSON.get("documentName"), '-');		
		tipo = "Publica";
		direccion =  (String) bibliotecaJSON.get("address") ;
		//codigoPostal = (int) bibliotecaJSON.get("postalcode");
		codigoPostal = transformString((String) bibliotecaJSON.get("postalcode"), '.');
		//longitud = (double) bibliotecaJSON.get("lonwgs84");
		longitud = Double.parseDouble(((String) bibliotecaJSON.get("lonwgs84")));
		//latitud = (double) bibliotecaJSON.get("latwgs84");
		latitud = Double.parseDouble((String) bibliotecaJSON.get("latwgs84"));
		//telefono = (int) bibliotecaJSON.get("phone");
			
			//char[] charSearch = {'-', '.', ' ', '(', ')', 'E', 'e', 'X', 'x', 'T', 't', 'L', 'l', 'U', 'u', 'Z', 'z'};
		telefono = (String) bibliotecaJSON.get("phone");
		
		email = (String) bibliotecaJSON.get("email");
		descripcion = transformString((String) bibliotecaJSON.get("documentDescription"), '-');
		
		
		localidad_nombre = stringSlicer((String) bibliotecaJSON.get("placename"), '-');
		
		//localidad_codigo = (int) bibliotecaJSON.get("placename");
		localidad_codigo = codigoPostal;
		provincia_nombre = (String) bibliotecaJSON.get("municipality");
		//provincia_codigo = (int) bibliotecaJSON.get("postalcode");
		provincia_codigo = ((String) bibliotecaJSON.get("postalcode")).substring(0, 2);
		
		}
		catch (Exception ex) { System.out.println(ex);}
		//char[] charSearch = {'-', '.'};
		//System.out.println(transformInts(codigoPostal, charSearch));
		
		//transformString(nombre, charSearch)
		System.out.println(nombre +tipo +direccion +codigoPostal +longitud +latitud +telefono +email +descripcion
				+localidad_nombre +localidad_codigo +provincia_nombre +provincia_codigo);
		
		
		/*String query = "INSERT INTO biblioteca VALUES (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, "
				+ "descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)";*/
		String query = "INSERT INTO biblioteca (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)"
				+ " VALUES ('"+nombre +"', '" +tipo +"', '" +direccion +"', '" +codigoPostal +"', '" +longitud +"', '" +latitud +"', '" +telefono +"', '" +email +"', '" +descripcion +"', '"
				+localidad_nombre +"', '" +localidad_codigo +"', '" +provincia_nombre +"', '" +provincia_codigo +"')";
		
		String query2 = "INSERT INTO biblioteca (nombre)"
				+ " VALUES ('"+nombre+"')";
		String query3 = "INSERT INTO biblioteca (nombre)"
				+ " VALUES ('Prueba')";
		
		
		Statement st = con.createStatement();
		
		st.executeUpdate(query);
		
	}
	
	
	
	public static int transformInt(String campo, char elemento) {
		StringBuilder sb = new StringBuilder(campo);
        //char[] charSearch = {'-','.'}; 
		char[] elementos = {elemento};

        for(int i=0; i<sb.length(); i++) 
        {
        	
            char chr = sb.charAt(i);
            //System.out.println("Compruebo: " +chr);
            //System.out.println("Length: " +sb.length());
            for(int j=0; j<elementos.length; j++)
            {
            	
                if(elementos[j] == chr)
                {    
                	if(i == sb.length()-1) {
                		sb.deleteCharAt(i);
                		i--;
                	}
                	else if(sb.charAt(i-1)== ' ' && sb.charAt(i+1)== ' ') {
                		sb.deleteCharAt(i); 
                		sb.deleteCharAt(i); 
                		i--;
                		i--;
                	}
                	else if (sb.charAt(i-1)== ' ' || sb.charAt(i+1)== ' ') {
                		sb.deleteCharAt(i);
                		i--;
                	}
                	else {
                		sb.deleteCharAt(i);
                		i--;
                	}
                                       
                }
            }
        }
       return Integer.parseInt(sb.toString());
	}
	
	public static double transformDouble(String campo, char[] elementos) {
		StringBuilder sb = new StringBuilder(campo);
        //char[] charSearch = {'-','.'}; 
		//char[] elementos = {elemento};

        for(int i=0; i<sb.length(); i++) 
        {
        	
            char chr = sb.charAt(i);
            //System.out.println("Compruebo: " +chr);
            //System.out.println("Length: " +sb.length());
            for(int j=0; j<elementos.length; j++)
            {
            	
                if(elementos[j] == chr)
                {    
                	if(i == sb.length()-1) {
                		sb.deleteCharAt(i);
                		i--;
                	}
                	else if(sb.charAt(i-1)== ' ' && sb.charAt(i+1)== ' ') {
                		sb.deleteCharAt(i); 
                		sb.deleteCharAt(i); 
                		i--;
                		i--;
                	}
                	else if (sb.charAt(i-1)== ' ' || sb.charAt(i+1)== ' ') {
                		sb.deleteCharAt(i);
                		i--;
                	}
                	else {
                		sb.deleteCharAt(i);
                		i--;
                	}
                                       
                }
            }
        }
        if(sb.toString() == "") { return 0.0;}
        
       return Double.parseDouble(sb.toString());
	}
	
	public static String transformString(String campo, char elemento) {
		StringBuilder sb = new StringBuilder(campo);
        //char[] charSearch = {'-','.'}; 
		
		char[] elementos = {elemento};

        for(int i=0; i<sb.length(); i++) 
        {
            char chr = sb.charAt(i);
            for(int j=0; j<elementos.length; j++)
            {
            	if(elementos[j] == chr)
                {   
            		if(i == sb.length()-1) {
                		sb.deleteCharAt(i);
                		i--;
                	}
            		else if(sb.charAt(i-1)== ' ' && sb.charAt(i+1)== ' ') {
                		sb.deleteCharAt(i); 
                		sb.deleteCharAt(i); 
                		i--;
                		i--;
                	}
                	else if (sb.charAt(i-1)== ' ' || sb.charAt(i+1)== ' ') {
                		sb.deleteCharAt(i); 
                		i--;
                	}
                	else {
                		sb.replace(i, i, " ");
                		i--;
                	}
                                       
                }
            }
        }
       return sb.toString();
	}
	
	public static String stringSlicer(String string, char from) {
		for(int i = 0; i<string.length(); i++) {
			if(string.charAt(i) == from) {
				
				string = string.substring(0, i);
				if(string.charAt(i-1)== ' ') {
					string = string.substring(0, i-1);
				}
			}
		}
		
		return string;
	}


}
