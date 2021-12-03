package wrappers;

public class StringCutter {
	
	public static int transformInt(String campo, char elemento) {
		StringBuilder sb = new StringBuilder(campo);
		// char[] charSearch = {'-','.'};
		char[] elementos = { elemento };

		for (int i = 0; i < sb.length(); i++) {

			char chr = sb.charAt(i);
			// System.out.println("Compruebo: " +chr);
			// System.out.println("Length: " +sb.length());
			for (int j = 0; j < elementos.length; j++) {

				if (elementos[j] == chr) {
					if (i == sb.length() - 1) {
						sb.deleteCharAt(i);
						i--;
					} else if (sb.charAt(i - 1) == ' ' && sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						sb.deleteCharAt(i);
						i--;
						i--;
					} else if (sb.charAt(i - 1) == ' ' || sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						i--;
					} else {
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
		// char[] charSearch = {'-','.'};
		// char[] elementos = {elemento};

		for (int i = 0; i < sb.length(); i++) {

			char chr = sb.charAt(i);
			// System.out.println("Compruebo: " +chr);
			// System.out.println("Length: " +sb.length());
			for (int j = 0; j < elementos.length; j++) {

				if (elementos[j] == chr) {
					if (i == sb.length() - 1) {
						sb.deleteCharAt(i);
						i--;
					} else if (sb.charAt(i - 1) == ' ' && sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						sb.deleteCharAt(i);
						i--;
						i--;
					} else if (sb.charAt(i - 1) == ' ' || sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						i--;
					} else {
						sb.deleteCharAt(i);
						i--;
					}

				}
			}
		}
		if (sb.toString() == "") {
			return 0.0;
		}

		return Double.parseDouble(sb.toString());
	}

	public static String transformString(String campo, char elemento) {
		StringBuilder sb = new StringBuilder(campo);
		// char[] charSearch = {'-','.'};

		char[] elementos = { elemento };

		for (int i = 0; i < sb.length(); i++) {
			char chr = sb.charAt(i);
			for (int j = 0; j < elementos.length; j++) {
				if (elementos[j] == chr) {
					if (i == sb.length() - 1) {
						sb.deleteCharAt(i);
						i--;
					} else if (sb.charAt(i - 1) == ' ' && sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						sb.deleteCharAt(i);
						i--;
						i--;
					} else if (sb.charAt(i - 1) == ' ' || sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						i--;
					} else {
						sb.replace(i, i, " ");
						i--;
					}

				}
			}
		}
		return sb.toString();
	}

	public static String transformString(String campo, char[] elementos) {
		StringBuilder sb = new StringBuilder(campo);
		// char[] charSearch = {'-','.'};

		for (int i = 0; i < sb.length(); i++) {
			char chr = sb.charAt(i);
			for (int j = 0; j < elementos.length; j++) {
				//System.out.println(sb.toString());
				if (elementos[j] == chr) {
					if (i == 0) {						
						sb.deleteCharAt(i);
						i--;
					} else if (i == sb.length() - 1) {
						sb.deleteCharAt(i);
						i--;
					} else if (sb.charAt(i - 1) == ' ' && sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						sb.deleteCharAt(i);
						i--;
						i--;
					} else if (sb.charAt(i - 1) == ' ' || sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						i--;
					} else {
						sb.deleteCharAt(i);
						i--;
					}

				}
			}
		}
		//System.out.println(sb.toString());
		return sb.toString();
	}

	public static String transformString(String campo, String elemento) {
		StringBuilder sb = new StringBuilder(campo);
		// char[] charSearch = {'-','.'};

		char[] elementos = { elemento.charAt(0) };

		for (int i = 0; i < sb.length(); i++) {
			char chr = sb.charAt(i);
			for (int j = 0; j < elementos.length; j++) {
				if (elementos[j] == chr) {
					if (i == sb.length() - 1) {
						sb.deleteCharAt(i);
						i--;
					} else if (sb.charAt(i - 1) == ' ' && sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						sb.deleteCharAt(i);
						i--;
						i--;
					} else if (sb.charAt(i - 1) == ' ' || sb.charAt(i + 1) == ' ') {
						sb.deleteCharAt(i);
						i--;
					} else {
						sb.replace(i, i, " ");
						i--;
					}

				}
			}
		}
		return sb.toString();
	}

	public static String stringSlicer(String string, char from) {
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == from) {

				string = string.substring(0, i);
				if (string.charAt(i - 1) == ' ') {
					string = string.substring(0, i - 1);
				}
			}
		}

		return string;
	}

	public static String nameProvincia(int num) {
		String nombre = "";

		// System.out.println(num);
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
			nombre = "Falta caso de: " + num;
		}

		return nombre;
	}

}
