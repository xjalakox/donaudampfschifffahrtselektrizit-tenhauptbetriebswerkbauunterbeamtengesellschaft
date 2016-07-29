package Terracraft;

import static java.lang.Math.toIntExact;

public final class Utils {
	
	public static int toInt(Object toConvert) {
		if (toConvert instanceof Integer) {
			int toReturn = (int) toConvert;
			return toReturn;
		} else if (toConvert instanceof String) {
			String toReturn = (String) toConvert;
			return Integer.parseInt(toReturn);
		} else if (toConvert instanceof Long) {
			Long toReturn = (Long) toConvert;
			return toIntExact(toReturn);
		} else if (toConvert instanceof Double) {
			Double toReturn = (Double) toConvert;
			return toReturn.intValue();
		}else if(toConvert instanceof Float) {
			Float toReturn = (Float) toConvert;
			return Math.round(toReturn);
		} else {
			System.out.println("Konnte Datentyp " + toConvert.getClass() + " nicht konvertieren!");
		}
		return 0;
	}

	public static String toString(Object toConvert) {
		if (toConvert instanceof Integer) {
			Integer toReturn = (Integer) toConvert;
			return toReturn + "";
		} else if (toConvert instanceof String) {
			String toReturn = (String) toConvert;
			return toReturn;
		} else if (toConvert instanceof Long) {
			Long toReturn = (Long) toConvert;
			return toReturn + "";
		} else if (toConvert instanceof Double) {
			Double toReturn = (Double) toConvert;
			return toReturn + "";
		} else if(toConvert instanceof Float) {
			Float toReturn = (Float) toConvert;
			return toReturn + "";
		}else {
			System.out.println("Konnte Datentyp " + toConvert.getClass() + " nicht konvertieren!");
		}
		return "";

	}

	public static boolean isNotNull(String toCheck) {
		if (toCheck != null && !toCheck.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isNotNull(String[] toCheck) {
		if (toCheck[0] != null && !toCheck[0].isEmpty()) {
			return true;
		}
		return false;
	}

	public static String removeLastChar(String str) {
		if (str.length() >= 1) {
			return str.substring(0, str.length() - 1);
		} else {
			return "";
		}
	}

	public static String removeFirstChar(String str) {
		if (str.length() >= 1) {
			return str.substring(1, str.length());
		} else {
			return "";
		}
	}

	

}
