package me.temper126.simple.utils;

import me.temper126.simple.SimplePlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.beans.ConstructorProperties;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringUtil {
	public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");
	public static final NumberFormat CURRENCY_FORMAT;

	public static String color(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static List<String> color(List<String> strings) {
		return (List) strings.stream().map(StringUtil::color).collect(Collectors.toList());
	}

	public static String strip(String string) {
		return ChatColor.stripColor(string);
	}

	public static String getChatColors(String string) {
		StringBuilder sb = new StringBuilder();
		int length = string.length();

		for (int index = 0; index < length; ++index) {
			char section = string.charAt(index);
			if ((section == '&' || section == 167) && index < length - 1) {
				char c = string.charAt(index + 1);
				ChatColor color = ChatColor.getByChar(c);
				if (color != null) {
					sb.append(color);
				}
			}
		}

		return sb.toString();
	}

	public static String repeat(String string, int amount) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < amount; ++i) {
			stringBuilder.append(string);
		}

		return stringBuilder.toString();
	}

	public static String center(String text) {
		text = color(text);
		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;
		char[] var4 = text.toCharArray();
		int toCompensate = var4.length;

		int spaceLength;
		for (spaceLength = 0; spaceLength < toCompensate; ++spaceLength) {
			char c = var4[spaceLength];
			if (c == 167) {
				previousCode = true;
			} else if (!previousCode) {
				StringUtil.DefaultFontInfo dFI = StringUtil.DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				++messagePxSize;
			} else {
				previousCode = false;
				isBold = c == 'l' || c == 'L';
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		toCompensate = 154 - halvedMessageSize;
		spaceLength = StringUtil.DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;

		StringBuilder stringBuilder;
		for (stringBuilder = new StringBuilder(); compensated < toCompensate; compensated += spaceLength) {
			stringBuilder.append(" ");
		}

		return stringBuilder + text;
	}

	public static int getCenterSpaceCount(String text) {
		text = color(text);
		int messagePxSize = 0;
		boolean previousCode = false;
		boolean isBold = false;
		char[] var4 = text.toCharArray();
		int toCompensate = var4.length;

		int spaceLength;
		for (spaceLength = 0; spaceLength < toCompensate; ++spaceLength) {
			char c = var4[spaceLength];
			if (c == 167) {
				previousCode = true;
			} else if (!previousCode) {
				StringUtil.DefaultFontInfo dFI = StringUtil.DefaultFontInfo.getDefaultFontInfo(c);
				messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
				++messagePxSize;
			} else {
				previousCode = false;
				isBold = c == 'l' || c == 'L';
			}
		}

		int halvedMessageSize = messagePxSize / 2;
		toCompensate = 154 - halvedMessageSize;
		spaceLength = StringUtil.DefaultFontInfo.SPACE.getLength() + 1;
		int compensated = 0;

		int spaceCount;
		for (spaceCount = 0; compensated < toCompensate; compensated += spaceLength) {
			++spaceCount;
		}

		return spaceCount;
	}

	public static boolean isInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (Exception var2) {
			return false;
		}
	}

	public static int tryParseInt(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception var2) {
			return -1;
		}
	}

	public static boolean isLong(String string) {
		try {
			Long.parseLong(string);
			return true;
		} catch (Exception var2) {
			return false;
		}
	}

	public static long tryParseLong(String string) {
		try {
			return Long.parseLong(string);
		} catch (Exception var2) {
			return -1L;
		}
	}

	public static double tryParsedouble(String string) {
		try {
			return Double.parseDouble(string);
		} catch (Exception var2) {
			return -1.0D;
		}
	}

	public static boolean isDouble(String string) {
		try {
			Double.parseDouble(string);
			return true;
		} catch (Exception var2) {
			return false;
		}
	}

	public static int stringToInt(String string) {
		if (string == null) {
			return -1;
		} else {
			string = strip(string.replaceAll("[^0-9]+", ""));
			return string.isEmpty() ? -1 : tryParseInt(string);
		}
	}

	public static String format(String string, Object... arguments) {
		if (arguments != null && arguments.length > 0) {
			for (int i = 0; i < arguments.length; ++i) {
				Object argument = arguments[i];
				string = string.replace(String.format("{%d}", i), argument == null ? "" : argument.toString());
			}
		}

		return string;
	}

	public static String colorFormat(String string, Object... arguments) {
		return color(format(string, arguments));
	}

	public static List<String> formatLore(String lore) {
		List<String> messages = new ArrayList();
		StringBuilder nextString = new StringBuilder(ChatColor.GRAY.toString());
		String[] var3 = lore.split(" ");
		int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			String string = var3[var5];
			if (string.length() <= 40) {
				if (nextString.length() + string.length() > 40) {
					messages.add(nextString.toString());
					nextString = new StringBuilder(ChatColor.GRAY + string + " ");
				} else {
					nextString.append(ChatColor.GRAY).append(string).append(" ");
				}
			}
		}

		messages.add(nextString.toString());
		return messages;
	}

	public static String formatMoney(double number) {
		return CURRENCY_FORMAT.format(number);
	}

	public static String formatMoneySymbol(double amount) {
		if (amount < 1000.0D) {
			return String.format("%s", DECIMAL_FORMAT.format(amount));
		} else {
			String symbol = "K";
			double divisorOne = 100.0D;
			double divisorTwo = 10.0D;
			if (amount >= 1.0E9D) {
				symbol = "B";
				divisorOne = 1000000.0D;
				divisorTwo = 1000.0D;
			} else if (amount >= 1000000.0D) {
				symbol = "M";
				divisorOne = 10000.0D;
				divisorTwo = 100.0D;
			}

			double clean = amount / divisorOne / divisorTwo;
			return String.format("%s%s", DECIMAL_FORMAT.format(clean), symbol);
		}
	}

	public static String insufficientBalanceMessage(Player player, double amount, String action) {
		double balance = SimplePlugin.getInstance().getEcon().getBalance(player);
		return balance < 0.0D ? format("&cSadly, you're -{0} in debt. You need {1} {2}.", formatMoney(Math.abs(balance)), formatMoney(amount), action) : format("&cYou only have {0}. You need {1} more {2}.", formatMoney(balance), formatMoney(amount - balance), action);
	}

	public static String getProgressBar(int current, int max, int totalBars) {
		return getProgressBar(current, max, totalBars, "|", ChatColor.GREEN, ChatColor.RED);
	}

	public static String getProgressBar(int current, int max, int totalBars, String bar, ChatColor progressColor, ChatColor leftColor) {
		float percent = (float) current / (float) max;
		int progressBars = (int) ((float) totalBars * percent);
		int leftOver = totalBars - progressBars;
		StringBuilder sb = new StringBuilder();
		sb.append(progressColor);

		int i;
		for (i = 0; i < progressBars; ++i) {
			sb.append(bar);
		}

		sb.append(leftColor);

		for (i = 0; i < leftOver; ++i) {
			sb.append(bar);
		}

		return sb.toString();
	}

	static {
		CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(Locale.US);
	}

	public static enum DefaultFontInfo {
		A('A', 5),
		a('a', 5),
		B('B', 5),
		b('b', 5),
		C('C', 5),
		c('c', 5),
		D('D', 5),
		d('d', 5),
		E('E', 5),
		e('e', 5),
		F('F', 5),
		f('f', 4),
		G('G', 5),
		g('g', 5),
		H('H', 5),
		h('h', 5),
		I('I', 3),
		i('i', 1),
		J('J', 5),
		j('j', 5),
		K('K', 5),
		k('k', 4),
		L('L', 5),
		l('l', 1),
		M('M', 5),
		m('m', 5),
		N('N', 5),
		n('n', 5),
		O('O', 5),
		o('o', 5),
		P('P', 5),
		p('p', 5),
		Q('Q', 5),
		q('q', 5),
		R('R', 5),
		r('r', 5),
		S('S', 5),
		s('s', 5),
		T('T', 5),
		t('t', 4),
		U('U', 5),
		u('u', 5),
		V('V', 5),
		v('v', 5),
		W('W', 5),
		w('w', 5),
		X('X', 5),
		x('x', 5),
		Y('Y', 5),
		y('y', 5),
		Z('Z', 5),
		z('z', 5),
		NUM_1('1', 5),
		NUM_2('2', 5),
		NUM_3('3', 5),
		NUM_4('4', 5),
		NUM_5('5', 5),
		NUM_6('6', 5),
		NUM_7('7', 5),
		NUM_8('8', 5),
		NUM_9('9', 5),
		NUM_0('0', 5),
		EXCLAMATION_POINT('!', 1),
		AT_SYMBOL('@', 6),
		NUM_SIGN('#', 5),
		DOLLAR_SIGN('$', 5),
		PERCENT('%', 5),
		UP_ARROW('^', 5),
		AMPERSAND('&', 5),
		ASTERISK('*', 5),
		LEFT_PARENTHESIS('(', 4),
		RIGHT_PERENTHESIS(')', 4),
		MINUS('-', 5),
		UNDERSCORE('_', 5),
		PLUS_SIGN('+', 5),
		EQUALS_SIGN('=', 5),
		LEFT_CURL_BRACE('{', 4),
		RIGHT_CURL_BRACE('}', 4),
		LEFT_BRACKET('[', 3),
		RIGHT_BRACKET(']', 3),
		COLON(':', 1),
		SEMI_COLON(';', 1),
		DOUBLE_QUOTE('"', 3),
		SINGLE_QUOTE('\'', 1),
		LEFT_ARROW('<', 4),
		RIGHT_ARROW('>', 4),
		QUESTION_MARK('?', 5),
		SLASH('/', 5),
		BACK_SLASH('\\', 5),
		LINE('|', 1),
		TILDE('~', 5),
		TICK('`', 2),
		PERIOD('.', 1),
		COMMA(',', 1),
		SPACE(' ', 3),
		DEFAULT('a', 4);

		private final char character;
		private final int length;

		public int getBoldLength() {
			return this == SPACE ? this.getLength() : this.length + 1;
		}

		public static StringUtil.DefaultFontInfo getDefaultFontInfo(char c) {
			StringUtil.DefaultFontInfo[] var1 = values();
			int var2 = var1.length;

			for (int var3 = 0; var3 < var2; ++var3) {
				StringUtil.DefaultFontInfo dFI = var1[var3];
				if (dFI.getCharacter() == c) {
					return dFI;
				}
			}

			return DEFAULT;
		}

		public char getCharacter() {
			return this.character;
		}

		public int getLength() {
			return this.length;
		}

		@ConstructorProperties({"character", "length"})
		private DefaultFontInfo(char character, int length) {
			this.character = character;
			this.length = length;
		}
	}
}
