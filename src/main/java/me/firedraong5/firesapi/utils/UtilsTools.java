package me.firedraong5.firesapi.utils;

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


}
