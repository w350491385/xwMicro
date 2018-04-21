package com.common.util;

public class RandomUtils {
	private static final String ALPHA_NUMERIC = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789";

	public static int getRandomInt(int min, int max) {
		return (int) (Math.random() * (double) (max - min + 1) + (double) min);
	}

	public static String getRandomNum(int length) {
		return Double.toString(Math.random()).substring(2, 2 + length);
	}

	public static String getRandomStr(int length) {
		char[] randomBytes = new char[length];

		for (int i = 0; i < length; ++i) {
			randomBytes[i] = "ABCDEFGHJKLMNPQRSTUVWXYZ123456789"
					.charAt(getRandomInt(0,
							"ABCDEFGHJKLMNPQRSTUVWXYZ123456789".length() - 1));
		}

		return new String(randomBytes);
	}

	public static String getRandomStrLowerCase(int length) {
		return getRandomStr(length).toLowerCase();
	}
}