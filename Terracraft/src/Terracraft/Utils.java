package Terracraft;

import static java.lang.Math.toIntExact;

import java.util.Random;

import crafting.Recipe;

public final class Utils {

	private static long millistimer;
	private static long nanostimer;

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
		} else if (toConvert instanceof Float) {
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
		} else if (toConvert instanceof Float) {
			Float toReturn = (Float) toConvert;
			return toReturn + "";
		} else if (toConvert instanceof Boolean) {
			Boolean toReturn = (Boolean) toConvert;
			return toReturn + "";
		} else if (toConvert instanceof Id) {
			Id toReturn = (Id) toConvert;
			return toReturn.toString();
		} else if (toConvert instanceof Recipe) {
			Recipe toReturn = (Recipe) toConvert;
			return toReturn.toString();
		} else {
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

	public static int RandomInt(int max) {
		Random random = new Random();
		int integer = random.nextInt(max);
		return integer;
	}

	public static int RandomInt(int min, int max) {
		Random random = new Random();
		int integer = random.nextInt(max) + min;
		return integer;
	}

	public static void startTimerMillis() {
		millistimer = System.currentTimeMillis();
	}

	public static String getTimerMillis() {
		long actualtime = System.currentTimeMillis();
		long difference = actualtime - millistimer;
		return "Gebrauchte Zeit " + difference + " Millisekunden";
	}

	public static void startTimerNanos() {
		nanostimer = System.nanoTime();
	}

	public static String getTimerNanos() {
		long actualtime = System.currentTimeMillis();
		long difference = actualtime - nanostimer;
		return "Gebrauchte Zeit " + difference + " Nanosekunden";
	}

}
