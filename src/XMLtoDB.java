import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

public class XMLtoDB {

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
        	
        	Object obj = jsonParser.parse(new FileReader("/home/dairor/eclipse-workspace/EuToJSON/src/biblioteques.json"));
        	
        	JSONObject jsonObject = (JSONObject) obj;
        	obj = jsonObject.get("response");
        	jsonObject = (JSONObject) obj;
        	obj = jsonObject.get("row");
        	//System.out.println(obj.toString());
			// A JSON array. JSONObject supports java.util.List interface.
			JSONArray listaBibliotecas = (JSONArray) obj;
			///listaBibliotecas = (JSONArray)listaBibliotecas.get(0);
			listaBibliotecas.forEach( biblioteca -> {
				
				try {
					/*if(j==3) {
						return;
					}
					j++;*/
					uploadBiblioteca((JSONObject)biblioteca);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			//fuploadBiblioteca(listaBibliotecas);
			
        }catch (Exception e) { System.out.println(e);}
	    
       
	    

	}
	//public static int j = 0;
	
	public static void uploadBiblioteca(JSONObject bibliotecaJSON) throws SQLException {
		
		int k = 0;
		try {
			//System.out.println("1");
			k++;
		nombre = transformString( bibliotecaJSON.get("nom").toString(), '.');
		nombre = transformString(nombre, "'");
		//System.out.println("2");
		k++;
		
		//System.out.println("3");
		direccion =  transformString( bibliotecaJSON.get("via").toString(), ',') ;
		k++;
		//System.out.println("4");
		direccion = transformString(direccion, '-');
		direccion = transformString(direccion, "'");
		k++;
		//System.out.println("5");
		//codigoPostal = (int) bibliotecaJSON.get("postalcode");
		codigoPostal =  bibliotecaJSON.get("cpostal").toString();
		k++;
		//System.out.println("6");
		//longitud = (double) bibliotecaJSON.get("lonwgs84");
		longitud = Double.parseDouble( bibliotecaJSON.get("longitud").toString());
		k++;
		//System.out.println("7");
		//latitud = (double) bibliotecaJSON.get("latwgs84");
		latitud = Double.parseDouble( bibliotecaJSON.get("latitud").toString());
		k++;
		//System.out.println("8");
		//telefono = (int) bibliotecaJSON.get("phone");
			
			//char[] charSearch = {'-', '.', ' ', '(', ')', 'E', 'e', 'X', 'x', 'T', 't', 'L', 'l', 'U', 'u', 'Z', 'z'};
		try {
		telefono =  bibliotecaJSON.get("telefon1").toString();
		}catch (Exception ex) {telefono = "";}
		k++;
		//System.out.println("9");
		email = bibliotecaJSON.get("email").toString();
		k++;
		//System.out.println("10");
		try {
		descripcion = transformString( bibliotecaJSON.get("propietats").toString(), '|');
		} catch(Exception ex){ descripcion = "";}//*/ "En marcha";
		k++;
		//System.out.println("11");
		
		localidad_nombre =  transformString(bibliotecaJSON.get("poblacio").toString(), "'");
		k++;
		//System.out.println("12");
		//localidad_codigo = (int) bibliotecaJSON.get("placename");
		localidad_codigo = bibliotecaJSON.get("codi_municipi").toString();
		k++;
		//System.out.println("13");
		
		//System.out.println("14");
		//provincia_codigo = (int) bibliotecaJSON.get("postalcode");
		provincia_codigo =  bibliotecaJSON.get("cpostal").toString().substring(0, 2);
		k++;
		
		provincia_nombre =  nameProvincia(Integer.parseInt(provincia_codigo));
		k++;
		
		if(descripcion.contains("Privada")) {
			tipo = "Privada";
		}else {tipo = "Pública";}
		//tipo = "ToDo";
		k++;
		//System.out.println("15");		
		}
		catch (Exception ex) {
			/*
			System.out.println(localidad_nombre);
			System.out.println(j +" " +k);*/
			System.out.println(ex);}
		//char[] charSearch = {'-', '.'};
		//System.out.println(transformInts(codigoPostal, charSearch));
		
		//transformString(nombre, charSearch)
		//System.out.println(nombre +tipo +direccion +codigoPostal +longitud +latitud +telefono +email +descripcion
		//		+localidad_nombre +localidad_codigo +provincia_nombre +provincia_codigo);
		
		
		System.out.println(j +" INSERT INTO bibliotecaCat (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)"
				+ " VALUES ('"+nombre +"', '" +tipo +"', '" +direccion +"', '" +codigoPostal +"', '" +longitud +"', '" +latitud +"', '" +telefono +"', '" +email +"', '" +descripcion +"', '"
				+localidad_nombre +"', '" +localidad_codigo +"', '" +provincia_nombre +"', '" +provincia_codigo +"')");
		
		j++;
		/*String query = "INSERT INTO biblioteca VALUES (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, "
				+ "descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)";*/
		String query = "INSERT INTO bibliotecaCat (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)"
				+ " VALUES ('"+nombre +"', '" +tipo +"', '" +direccion +"', '" +codigoPostal +"', '" +longitud +"', '" +latitud +"', '" +telefono +"', '" +email +"', '" +descripcion +"', '"
				+localidad_nombre +"', '" +localidad_codigo +"', '" +provincia_nombre +"', '" +provincia_codigo +"')";
		
		String query2 = "INSERT INTO biblioteca (nombre)"
				+ " VALUES ('"+nombre+"')";
		String query3 = "INSERT INTO biblioteca (nombre)"
				+ " VALUES ('Prueba')";
		
		
		Statement st = con.createStatement();
		try {
		st.executeUpdate(query);
		} catch (Exception ex) { 
			/*
			System.out.println(localidad_nombre);
			System.out.println(j +" " +nombre);
			*/
			descripcion = "";
			query = "INSERT INTO bibliotecaCat (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)"
					+ " VALUES ('"+nombre +"', '" +tipo +"', '" +direccion +"', '" +codigoPostal +"', '" +longitud +"', '" +latitud +"', '" +telefono +"', '" +email +"', '" +descripcion +"', '"
					+localidad_nombre +"', '" +localidad_codigo +"', '" +provincia_nombre +"', '" +provincia_codigo +"')";
		st.executeUpdate(query);
		}
		
	}
	public static int j = 1;
	
	
	
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
	
	public static String transformString(String campo, String elemento) {
		StringBuilder sb = new StringBuilder(campo);
        //char[] charSearch = {'-','.'}; 
		
		char[] elementos = {elemento.charAt(0)};

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
	
	public static String nameProvincia(int num) {
		String nombre = "";
		
		//System.out.println(num);
		switch (num) {
		case 8: {
			
			nombre = "Barcelona";
		}
		break;
		case 17: {
			nombre = "Girona";
		}
		break;
		case 25: {
			nombre = "Lleida";
			
		}
		break;
		case 43: {
			nombre = "Tarragona";
		}
		break;
		default:
			nombre = "Falta caso de: " +num;
		}
		
		
		return nombre;
	}

}
