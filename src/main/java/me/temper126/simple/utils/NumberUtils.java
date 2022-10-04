package me.temper126.simple.utils;

import java.util.concurrent.ThreadLocalRandom;

public class NumberUtils {

	public static String format(final double number, final DecimalFormatType type) {
		return type.getFormat().format(number);
	}

	public static String formatMoney(final double number) {
		return format(number, DecimalFormatType.MONEY);
	}

	public static String formatSeconds(final double number) {
		return format(number, DecimalFormatType.SECONDS);
	}

	public static Long parseLongOrNull(final String val) {
		try {
			return Long.parseLong(val);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Integer parseIntOrNull(final String val) {
		try {
			return Integer.parseInt(val);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Double parseDoubleOrNull(final String val) {
		try {
			return Double.parseDouble(val);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Float parseFloatOrNull(final String val) {
		try {
			return Float.parseFloat(val);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Number parseNumberOrNull(final String val) {
		final Integer found = parseIntOrNull(val);
		if (found != null) {
			return found;
		}
		final Long longFound = parseLongOrNull(val);
		if (longFound != null) {
			return longFound;
		}
		final Double doubleFound = parseDoubleOrNull(val);
		if (doubleFound != null) {
			return doubleFound;
		}
		final Float floatFound = parseFloatOrNull(val);
		if (floatFound != null) {
			return floatFound;
		}
		return null;
	}

	public static int roundWithChance(double val) {
		int valMag = (int) val;
		double chanceRound = val - valMag;

		if (ThreadLocalRandom.current().nextDouble() < chanceRound) {
			return valMag + 1;
		} else {
			return valMag;
		}
	}

}