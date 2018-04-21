package com.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArrayUtils {
	public static String toString(String[] args) {
		return toString(args, ",");
	}

	public static String toString(String[] args, String separator) {
		if (args != null && args.length != 0) {
			StringBuilder buffer = new StringBuilder();

			for (int i = 0; i < args.length; ++i) {
				if (i > 0) {
					buffer.append(separator);
				}

				buffer.append(args[i]);
			}

			return buffer.toString();
		} else {
			return null;
		}
	}

	public static String getFirst(String[] stringArray) {
		return stringArray != null && stringArray.length != 0
				? stringArray[0]
				: null;
	}

	public static Object getFirst(Object[] array) {
		return array != null && array.length != 0 ? array[0] : null;
	}

	public static String[] toArray(List<String> list) {
		return (String[]) list.toArray(new String[list.size()]);
	}

	public static String[] toArray(Set<String> set) {
		return (String[]) set.toArray(new String[set.size()]);
	}

	public static boolean contains(String[] array, String str) {
		if (array != null && array.length != 0) {
			for (int i = 0; i < array.length; ++i) {
				if (array[i] == null && str == null) {
					return true;
				}

				if (array[i].equals(str)) {
					return true;
				}
			}

			return false;
		} else {
			return false;
		}
	}

	public static boolean hasValue(String[] args) {
		if (args != null && args.length != 0
				&& (args.length != 1 || args[0] != null)) {
			int i = 0;

			for (int length = args.length; i < length; ++i) {
				if (args[i] != null || args[i].trim().length() > 0) {
					return true;
				}
			}

			return false;
		} else {
			return false;
		}
	}

	public static Object[] combine(Object[] first, Object[] last) {
		if (first.length == 0 && last.length == 0) {
			return null;
		} else {
			Object[] result = new Object[first.length + last.length];
			System.arraycopy(first, 0, result, 0, first.length);
			System.arraycopy(last, 0, result, first.length, last.length);
			return result;
		}
	}

	public static List<Object> toList(Object[] array) {
		ArrayList list = new ArrayList();
		if (array == null) {
			return list;
		} else {
			for (int i = 0; i < array.length; ++i) {
				list.add(array[i]);
			}

			return list;
		}
	}

	public static String[] clearNull(String[] array) {
		ArrayList list = new ArrayList();

		for (int i = 0; i < array.length; ++i) {
			if (array[i] != null) {
				list.add(array[i]);
			}
		}

		return toArray((List) list);
	}
}