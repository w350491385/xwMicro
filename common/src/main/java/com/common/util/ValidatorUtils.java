package com.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidatorUtils {
	private static final String REGEX_SIMPLE_CHINESE = "^[一-龥]+$";
	private static final String REGEX_ALPHANUMERIC = "[a-zA-Z0-9]+";
	private static final String REGEX_NUMERIC = "(\\+|-){0,1}(\\d+)([.]?)(\\d*)";
	private static final String REGEX_ID_CARD = "(\\d{14}|\\d{17})(\\d|x|X)";
	private static final String REGEX_EMAIL = ".+@.+\\.[a-z]+";

	public static boolean isAlphanumeric(String str) {
		return isRegexMatch(str, "[a-zA-Z0-9]+");
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str != null && (strLen = str.length()) != 0) {
			for (int i = 0; i < strLen; ++i) {
				if (!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public static boolean isDate(String str) {
		if (!isEmpty(str) && str.length() <= 10) {
			String[] items = str.split("-");
			if (items.length != 3) {
				return false;
			} else if (isNumber(items[0], 1900, 9999)
					&& isNumber(items[1], 1, 12)) {
				int year = Integer.parseInt(items[0]);
				int month = Integer.parseInt(items[1]);
				return isNumber(items[2], 1,
						DateUtils.getMaxDayOfMonth(year, month - 1));
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static boolean isDateTime(String str) {
		if (!isEmpty(str) && str.length() <= 20) {
			String[] items = str.split(" ");
			return items.length != 2 ? false : isDate(items[0])
					&& isTime(items[1]);
		} else {
			return false;
		}
	}

	public static boolean isEmail(String str) {
		return isRegexMatch(str, ".+@.+\\.[a-z]+");
	}

	public static boolean isEmpty(Object[] args) {
		return args == null || args.length == 0 || args.length == 1
				&& args[0] == null;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static <T> boolean isEmpty(Collection<T> collection) {
		return collection == null || collection.isEmpty();
	}

	public static <K, V> boolean isEmptyMap(Map<K, V> map) {
		return map == null || map.isEmpty();
	}

	public static boolean isIdCardNumber(String str) {
		return isRegexMatch(str, "(\\d{14}|\\d{17})(\\d|x|X)");
	}

	public static boolean isNumber(String str) {
		if (isEmpty(str)) {
			return false;
		} else {
			for (int i = 0; i < str.length(); ++i) {
				if (str.charAt(i) > 57 || str.charAt(i) < 48) {
					return false;
				}
			}

			return true;
		}
	}

	public static boolean isNumber(String str, int min, int max) {
		if (!isNumber(str)) {
			return false;
		} else {
			int number = Integer.parseInt(str);
			return number >= min && number <= max;
		}
	}

	public static boolean isNumeric(String str) {
		return isRegexMatch(str, "(\\+|-){0,1}(\\d+)([.]?)(\\d*)");
	}

	public static boolean isNumeric(String str, int fractionNum) {
		if (isEmpty(str)) {
			return false;
		} else {
			String regex = "(\\+|-){0,1}(\\d+)([.]?)(\\d{0," + fractionNum
					+ "})";
			return Pattern.matches(regex, str);
		}
	}

	public static boolean isPostcode(String str) {
		return isEmpty(str) ? false : str.length() == 6 && isNumber(str);
	}

	public static boolean isString(String str, int minLength, int maxLength) {
		return str == null ? false : (minLength < 0
				? str.length() <= maxLength
				: (maxLength < 0
						? str.length() >= minLength
						: str.length() >= minLength
								&& str.length() <= maxLength));
	}

	public static boolean isTime(String str) {
		if (!isEmpty(str) && str.length() <= 8) {
			String[] items = str.split(":");
			if (items.length != 2 && items.length != 3) {
				return false;
			} else {
				for (int i = 0; i < items.length; ++i) {
					if (items[i].length() != 2 && items[i].length() != 1) {
						return false;
					}
				}

				return isNumber(items[0], 0, 23) && isNumber(items[1], 0, 59)
						&& (items.length != 3 || isNumber(items[2], 0, 59));
			}
		} else {
			return false;
		}
	}

	public static boolean isSimpleChinese(String str) {
		return isRegexMatch(str, "^[一-龥]+$");
	}

	public static boolean isRegexMatch(String str, String regex) {
		return str != null && str.matches(regex);
	}
}