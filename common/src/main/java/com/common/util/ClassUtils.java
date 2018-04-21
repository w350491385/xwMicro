package com.common.util;

import java.lang.reflect.Constructor;

public class ClassUtils {
	public static boolean hasDefaultConstructor(Class<?> clazz) {
		Constructor[] arg0 = clazz.getDeclaredConstructors();
		int arg1 = arg0.length;

		for (int arg2 = 0; arg2 < arg1; ++arg2) {
			Constructor constructor = arg0[arg2];
			if (!constructor.isVarArgs()) {
				return true;
			}
		}

		return false;
	}
}