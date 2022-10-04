package me.temper126.simple.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {

	public static String getRemaining(int seconds, TimeFormat type) {
		if (seconds < 60) {
			switch (type) {
				case DAYS:
					return "0";
				case HOURS:
					return "0";
				case MINUTES:
					return "0";
				case SECONDS:
					return String.valueOf(seconds);
				default:
					return String.valueOf(seconds);
			}
		} else {
			int minutes = seconds / 60;
			int s = 60 * minutes;
			int secondsLeft = seconds - s;
			if (minutes < 60) {
				switch (type) {
					case DAYS:
						return "0";
					case HOURS:
						return "0";
					case MINUTES:
						return String.valueOf(minutes);
					case SECONDS:
						return String.valueOf(secondsLeft);
					default:
						return String.valueOf(seconds);
				}
			} else {
				int days;
				int inMins;
				int leftOver;
				if (minutes < 1440) {
					days = minutes / 60;
					inMins = 60 * days;
					leftOver = minutes - inMins;
					switch (type) {
						case DAYS:
							return "0";
						case HOURS:
							return String.valueOf(days);
						case MINUTES:
							return String.valueOf(leftOver);
						case SECONDS:
							return String.valueOf(secondsLeft);
						default:
							return String.valueOf(seconds);
					}
				} else {
					days = minutes / 1440;
					inMins = 1440 * days;
					leftOver = minutes - inMins;
					if (leftOver < 60) {
						switch (type) {
							case DAYS:
								return String.valueOf(days);
							case HOURS:
								return String.valueOf(0);
							case MINUTES:
								return String.valueOf(leftOver);
							case SECONDS:
								return String.valueOf(secondsLeft);
							default:
								return String.valueOf(seconds);
						}
					} else {
						int hours = leftOver / 60;
						int hoursInMins = 60 * hours;
						int minsLeft = leftOver - hoursInMins;
						switch (type) {
							case DAYS:
								return String.valueOf(days);
							case HOURS:
								return String.valueOf(hours);
							case MINUTES:
								return String.valueOf(minsLeft);
							case SECONDS:
								return String.valueOf(secondsLeft);
							default:
								return String.valueOf(seconds);
						}
					}
				}
			}
		}
	}

	public static String getTime(int seconds) {
		if (seconds < 60) {
			return seconds + "s";
		} else {
			int minutes = seconds / 60;
			int s = 60 * minutes;
			int secondsLeft = seconds - s;
			if (minutes < 60) {
				return secondsLeft > 0 ? String.valueOf(minutes + "m " + secondsLeft + "s") : String.valueOf(minutes + "m");
			} else {
				String time;
				int days;
				int inMins;
				int leftOver;
				if (minutes < 1440) {
					days = minutes / 60;
					time = days + "h";
					inMins = 60 * days;
					leftOver = minutes - inMins;
					if (leftOver >= 1) {
						time = time + " " + leftOver + "m";
					}

					if (secondsLeft > 0) {
						time = time + " " + secondsLeft + "s";
					}

					return time;
				} else {
					days = minutes / 1440;
					time = days + "d";
					inMins = 1440 * days;
					leftOver = minutes - inMins;
					if (leftOver >= 1) {
						if (leftOver < 60) {
							time = time + " " + leftOver + "m";
						} else {
							int hours = leftOver / 60;
							time = time + " " + hours + "h";
							int hoursInMins = 60 * hours;
							int minsLeft = leftOver - hoursInMins;
							time = time + " " + minsLeft + "m";
						}
					}

					if (secondsLeft > 0) {
						time = time + " " + secondsLeft + "s";
					}

					return time;
				}
			}
		}
	}

	/**
	 * From KrizzUtil in Kits
	 */

	public static String formatTime(final int secs) {
		final int remainder = secs % 86400;
		final int days = secs / 86400;
		final int hours = remainder / 3600;
		final int minutes = remainder / 60 - hours * 60;
		final int seconds = remainder % 3600 - minutes * 60;
		final String fDays = (days > 0) ? (" " + days + " day" + ((days > 1) ? "s" : "")) : "";
		final String fHours = (hours > 0) ? (" " + hours + " hour" + ((hours > 1) ? "s" : "")) : "";
		final String fMinutes = (minutes > 0) ? (" " + minutes + " minute" + ((minutes > 1) ? "s" : "")) : "";
		final String fSeconds = (seconds > 0) ? (" " + seconds + " second" + ((seconds > 1) ? "s" : "")) : "";
		final String result = String.valueOf(fDays) + fHours + fMinutes + fSeconds;
		if (result.equals("")) {
			return "0 seconds";
		}
		return result.substring(1, result.length());
	}

	public static String formatTime(long secs) {
		secs /= 1000L;
		final long remainder = secs % 86400L;
		final long days = secs / 86400L;
		final long hours = remainder / 3600L;
		final long minutes = remainder / 60L - hours * 60L;
		final long seconds = remainder % 3600L - minutes * 60L;
		final String fDays = (days > 0L) ? (" " + days + " day" + ((days > 1L) ? "s" : "")) : "";
		final String fHours = (hours > 0L) ? (" " + hours + " hour" + ((hours > 1L) ? "s" : "")) : "";
		final String fMinutes = (minutes > 0L) ? (" " + minutes + " minute" + ((minutes > 1L) ? "s" : "")) : "";
		final String fSeconds = (seconds > 0L) ? (" " + seconds + " second" + ((seconds > 1L) ? "s" : "")) : "";
		final String result = String.valueOf(fDays) + fHours + fMinutes + fSeconds;
		if (result.equals("")) {
			return " 0 seconds";
		}
		return result;
	}

	public static String formatTime(final long secs, final boolean div) {
		final long remainder = secs % 86400L;
		final long days = secs / 86400L;
		final long hours = remainder / 3600L;
		final long minutes = remainder / 60L - hours * 60L;
		final long seconds = remainder % 3600L - minutes * 60L;
		final String fDays = (days > 0L) ? (" " + days + " day" + ((days > 1L) ? "s" : "")) : "";
		final String fHours = (hours > 0L) ? (" " + hours + " hour" + ((hours > 1L) ? "s" : "")) : "";
		final String fMinutes = (minutes > 0L) ? (" " + minutes + " minute" + ((minutes > 1L) ? "s" : "")) : "";
		final String fSeconds = (seconds > 0L) ? (" " + seconds + " second" + ((seconds > 1L) ? "s" : "")) : "";
		final String result = String.valueOf(fDays) + fHours + fMinutes + fSeconds;
		if (result.equals("")) {
			return " 0 seconds";
		}
		return result;
	}

	private static final Pattern periodPattern = Pattern.compile("([0-9]+)([hdwmy])");

	public static Long parsePeriod(String period) {
		if (period == null) return null;
		period = period.toLowerCase(Locale.ENGLISH);
		Matcher matcher = periodPattern.matcher(period);
		Instant instant = Instant.EPOCH;
		while (matcher.find()) {
			int num = Integer.parseInt(matcher.group(1));
			String typ = matcher.group(2);
			switch (typ) {
				case "h":
					instant = instant.plus(Duration.ofHours(num));
					break;
				case "d":
					instant = instant.plus(Duration.ofDays(num));
					break;
				case "w":
					instant = instant.plus(Period.ofWeeks(num));
					break;
				case "m":
					instant = instant.plus(Period.ofMonths(num));
					break;
				case "y":
					instant = instant.plus(Period.ofYears(num));
					break;
			}
		}
		return instant.toEpochMilli();
	}

	public static String formatFutureTime(final long timeInFuture) {
		final long timeDifference = timeInFuture - System.currentTimeMillis();
		final long seconds = timeDifference / 1000L;
		return formatSeconds(seconds);
	}

	public static String formatSeconds(final long seconds) {
		if (seconds == 0L) {
			return "0s";
		}
		final long day = TimeUnit.SECONDS.toDays(seconds);
		final long hours = TimeUnit.SECONDS.toHours(seconds) - day * 24L;
		final long minutes = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
		final long secs = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
		final StringBuilder sb = new StringBuilder();
		if (day > 0L) {
			sb.append(day).append((day == 1L) ? "d" : "d").append(" ");
		}
		if (hours > 0L) {
			sb.append(hours).append((hours == 1L) ? "h" : "h").append(" ");
		}
		if (minutes > 0L) {
			sb.append(minutes).append((minutes == 1L) ? "m" : "m").append(" ");
		}
		if (secs > 0L) {
			sb.append(secs).append((secs == 1L) ? "s" : "s");
		}
		final String diff = sb.toString();
		return diff.isEmpty() ? "Now" : diff.trim();
	}

	public enum TimeFormat {
		DAYS,
		HOURS,
		MINUTES,
		SECONDS
	}


}
