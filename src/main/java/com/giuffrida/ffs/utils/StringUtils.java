package com.giuffrida.ffs.utils;

public class StringUtils {

	public static String stripQuotes(String string) {

		if (StringUtils.isEmpty(string)) {
			return string;
		}

		if (!string.contains("\"")) {
			return string;
		}

		return string.replace("\"", "");
	}

	public static boolean isEmpty(String s) {
		return (s == null || s.trim().length() == 0);
	}

	public static Boolean castYesNoToBoolean(String s) {
		if (isEmpty(s)) {
			return null;
		} else if (s.equalsIgnoreCase("Y")) {
			return true;
		} else if (s.equalsIgnoreCase("N")) {
			return false;
		} else {
			return null;
		}
	}
}
