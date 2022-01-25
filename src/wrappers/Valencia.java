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
import conexiones.*;

public class Valencia {

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
		// Connection con = null;

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
			Object obj = jsonParser.parse(new FileReader("/home/dairor/eclipse-workspace/EuToJSON/src/JSON/CV.json"));

			JSONArray listaBibliotecas = (JSONArray) obj;
			
			listaBibliotecas.forEach(biblioteca -> {
				
				try {
					
					uploadBiblioteca((JSONObject) biblioteca);

				} catch (SQLException e) {
					e.printStackTrace();
				}
			});

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void uploadBiblioteca(JSONObject bibliotecaJSON) throws SQLException {

		try {
			nombre = bibliotecaJSON.get("NOMBRE").toString();
			
			if (bibliotecaJSON.get("COD_CARACTER").toString().contains("PR")) { tipo = "Privada";}
			else { tipo = "Pública";}
			
			direccion = (String) bibliotecaJSON.get("DIRECCION");
			
			if (bibliotecaJSON.get("CP").toString().length() == 4) { codigoPostal = "0" +bibliotecaJSON.get("CP").toString();}
			else { codigoPostal = bibliotecaJSON.get("CP").toString();}
			
			String[] location = GeocodeLocation.getLocation(direccion +", " +bibliotecaJSON.get("NOM_PROVINCIA").toString());
			
			try {
			 longitud = Double.parseDouble(location[0]);
			 latitud = Double.parseDouble(location[1]);
			}catch(Exception ex) {longitud = 0; latitud = 0;}
			
			char[] charSearch = { '-', '.', ' ', '(', ')', 'E', 'e', 'X', 'x', 'T', 't', 'L', 'l', 'U', 'u', 'Z', 'z', 'F', 'f', 'A', 'a', 'Y', 'y', 'O', 'o' };
			telefono = StringCutter.transformString(bibliotecaJSON.get("TELEFONO").toString(), charSearch);
			email = (String) bibliotecaJSON.get("EMAIL");
			descripcion = bibliotecaJSON.get("TIPO").toString();
			localidad_nombre = bibliotecaJSON.get("NOM_MUNICIPIO").toString();

			
			
						
			provincia_nombre = bibliotecaJSON.get("NOM_PROVINCIA").toString();
			
			if (bibliotecaJSON.get("COD_PROVINCIA").toString().length() == 1) { provincia_codigo = "0" +bibliotecaJSON.get("COD_PROVINCIA").toString();}
			else { provincia_codigo = bibliotecaJSON.get("COD_PROVINCIA").toString(); }
			
			if (bibliotecaJSON.get("COD_MUNICIPIO").toString().length() == 2) { localidad_codigo = provincia_codigo +"0" +bibliotecaJSON.get("COD_MUNICIPIO").toString();}
			else { localidad_codigo = provincia_codigo +bibliotecaJSON.get("COD_MUNICIPIO").toString(); }

		} catch (Exception ex) {
			System.out.println(ex);
		}
		
		System.out.println(j +nombre + tipo + direccion + codigoPostal + longitud + latitud + telefono + email
				+ descripcion + localidad_nombre + localidad_codigo + provincia_nombre + provincia_codigo);

		j++;
		
		String query = "INSERT INTO bibliotecas (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, descripcion, localidad_codigo, provincia_codigo)"
				+ " VALUES ('"+nombre +"', '" +tipo +"', '" +direccion +"', '" +codigoPostal +"', '" +longitud +"', '" +latitud +"', '" +telefono +"', '" +email +"', '" +descripcion +"', '"
				 +localidad_codigo +"', '" +provincia_codigo +"')";
		
		String queryLocalidades = "INSERT INTO localidades (localidad_nombre, localidad_codigo)"
				+ " VALUES ('"+localidad_nombre +"', '" +localidad_codigo +"')";
		
		String queryProvincias = "INSERT INTO provincias (provincia_nombre, provincia_codigo)"
				+ " VALUES ('"+provincia_nombre +"', '" +provincia_codigo +"')";
		
		
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
		}catch(Exception ex) {
			System.out.println(ex);
		}

	}

	

}
