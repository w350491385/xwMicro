package com.common.util;

import java.math.BigDecimal;

public abstract class MathUtils {
	private static final int DEFAULT_DIV_SCALE = 10;

	public static double add(double v1, double v2) {
		BigDecimal b1 = createBigDecimal(v1);
		BigDecimal b2 = createBigDecimal(v2);
		return b1.add(b2).doubleValue();
	}

	public static double div(double v1, double v2) {
		return div(v1, v2, 10);
	}

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		} else {
			BigDecimal b1 = createBigDecimal(v1);
			BigDecimal b2 = createBigDecimal(v2);
			return b1.divide(b2, scale, 4).doubleValue();
		}
	}

	public static boolean isInvalidDouble(double v) {
		return Double.isInfinite(v) || Double.isNaN(v);
	}

	public static double mul(double v1, double v2) {
		BigDecimal b1 = createBigDecimal(v1);
		BigDecimal b2 = createBigDecimal(v2);
		return b1.multiply(b2).doubleValue();
	}

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		} else if (isInvalidDouble(v)) {
			return v;
		} else {
			BigDecimal b = createBigDecimal(v);
			return b.divide(BigDecimal.ONE, scale, 4).doubleValue();
		}
	}

	public static double sub(double v1, double v2) {
		BigDecimal b1 = createBigDecimal(v1);
		BigDecimal b2 = createBigDecimal(v2);
		return b1.subtract(b2).doubleValue();
	}

	private static BigDecimal createBigDecimal(double v) {
		return new BigDecimal(Double.toString(v));
	}
}