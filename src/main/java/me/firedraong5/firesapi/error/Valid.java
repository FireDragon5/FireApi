package me.firedraong5.firesapi.error;

public class Valid {


	/**
	 * Checks if the given object is null and throws a NullPointerException if it is.
	 *
	 * @param object the object to check
	 * @param message the message to include in the exception
	 * @throws NullPointerException if the object is null
	 */
	public static void checkNotNull(Object object, String message) {
		if (object == null) {
			throw new NullPointerException(message);
		}
	}



}
