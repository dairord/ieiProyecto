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

		/*
		 * Statement st = con.createStatement();
		 * 
		 * String query =
		 * "INSERT INTO biblioteca VALUES (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, "
		 * +
		 * "descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo"
		 * ;
		 */

// note that i'm leaving "date_created" out of this insert statement

		// st.executeQuery(query);

		try {
			Object obj = jsonParser.parse(new FileReader("/home/dairor/eclipse-workspace/EuToJSON/src/JSON/CV.json"));

			// JSONObject jsonObject = (JSONObject) obj;

			// A JSON array. JSONObject supports java.util.List interface.
			JSONArray listaBibliotecas = (JSONArray) obj;
			
			System.out.println(listaBibliotecas.size());

			listaBibliotecas.forEach(biblioteca -> {
				System.out.println("nany?");
				try {
					
					System.out.println("wat");
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
			nombre = bibliotecaJSON.get("NOM_PROVINCIA").toString();
			
			if (bibliotecaJSON.get("COD_CARACTER").toString().contains("PR")) { tipo = "PRIVADA";}
			else { tipo = "PÚBLICA";}
			
			direccion = (String) bibliotecaJSON.get("DIRECCION");
			// codigoPostal = (int) bibliotecaJSON.get("postalcode");
			
			if (bibliotecaJSON.get("CP").toString().length() == 4) { codigoPostal = "0" +bibliotecaJSON.get("CP").toString();}
			else { codigoPostal = bibliotecaJSON.get("CP").toString();}
			
			String[] location = GeocodeLocation.getLocation(direccion +", " +nombre);
			try {
			 longitud = Double.parseDouble(location[0]);
			// longitud = Double.parseDouble(((String) bibliotecaJSON.get("lonwgs84")));
			 latitud = Double.parseDouble(location[1]);
			}catch(Exception ex) {longitud = 0; latitud = 0;}
			// latitud = Double.parseDouble((String) bibliotecaJSON.get("latwgs84"));
			// telefono = (int) bibliotecaJSON.get("phone");

			char[] charSearch = { '-', '.', ' ', '(', ')', 'E', 'e', 'X', 'x', 'T', 't', 'L', 'l', 'U', 'u', 'Z', 'z', 'F', 'f', 'A', 'a', 'Y', 'y', 'O', 'o' };
			telefono = StringCutter.transformString(bibliotecaJSON.get("TELEFONO").toString(), charSearch);

			email = (String) bibliotecaJSON.get("EMAIL");
			 descripcion = bibliotecaJSON.get("TIPO").toString();
			// bibliotecaJSON.get("documentDescription"), '-');

			localidad_nombre = bibliotecaJSON.get("NOM_MUNICIPIO").toString();

			// localidad_codigo = (int) bibliotecaJSON.get("placename");
			//localidad_codigo = bibliotecaJSON.get("COD_MUNICIPIO").toString();
			
			if (bibliotecaJSON.get("COD_MUNICIPIO").toString().length() == 2) { localidad_codigo = "0" +bibliotecaJSON.get("COD_MUNICIPIO").toString();}
			else { localidad_codigo = bibliotecaJSON.get("COD_MUNICIPIO").toString(); }
						
			provincia_nombre = bibliotecaJSON.get("NOM_PROVINCIA").toString();
			// provincia_codigo = (int) bibliotecaJSON.get("postalcode");
			
			if (bibliotecaJSON.get("COD_PROVINCIA").toString().length() == 1) { provincia_codigo = "0" +bibliotecaJSON.get("COD_PROVINCIA").toString();}
			else { provincia_codigo = bibliotecaJSON.get("COD_PROVINCIA").toString(); }
			//provincia_codigo = bibliotecaJSON.get("COD_PROVINCIA").toString();

		} catch (Exception ex) {
			System.out.println(ex);
		}
		// char[] charSearch = {'-', '.'};
		// System.out.println(transformInts(codigoPostal, charSearch));

		// transformString(nombre, charSearch)
		System.out.println(nombre + tipo + direccion + codigoPostal + longitud + latitud + telefono + email
				+ descripcion + localidad_nombre + localidad_codigo + provincia_nombre + provincia_codigo);

		/*
		 * String query =
		 * "INSERT INTO biblioteca VALUES (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, "
		 * +
		 * "descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)"
		 * ;
		 */
		String query = "INSERT INTO bibliotecaCSV (nombre, tipo, direccion, codigoPostal, longitud, latitud, telefono, email, descripcion, localidad_nombre, localidad_codigo, provincia_nombre, provincia_codigo)"
				+ " VALUES ('" + nombre + "', '" + tipo + "', '" + direccion + "', '" + codigoPostal + "', '" + longitud
				+ "', '" + latitud + "', '" + telefono + "', '" + email + "', '" + descripcion + "', '"
				+ localidad_nombre + "', '" + localidad_codigo + "', '" + provincia_nombre + "', '" + provincia_codigo
				+ "')";

		String query2 = "INSERT INTO biblioteca (nombre)" + " VALUES ('" + nombre + "')";
		String query3 = "INSERT INTO biblioteca (nombre)" + " VALUES ('Prueba')";

		Statement st = con.createStatement();

		st.executeUpdate(query);

	}

	

}
