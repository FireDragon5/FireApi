package me.firedragon5.firesapi.utils;

@SuppressWarnings("unused")
public class UtilsTools {


	//	isint
	public static boolean isInt(String string) {

		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	//	isdouble
	public static boolean isDouble(String string) {

		try {
			Double.parseDouble(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	public static boolean isNull(Object object) {
		return object == null;
	}

//	Check if a string or list is empty
	public static boolean isEmpty(String string) {
		return string == null || string.isEmpty();
	}

}
