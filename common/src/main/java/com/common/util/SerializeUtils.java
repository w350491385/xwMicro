package com.common.util;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSONObject;

public class SerializeUtils {
	private static final Charset CHARSET;

	public static byte[] encode(Object stuff) {
		return JSONObject.toJSONString(stuff).getBytes(CHARSET);
	}

	public static Object decode(String stuff, Class<?> clazz) {
		return JSONObject.parseObject(stuff, clazz);
	}

	static {
		CHARSET = Charsets.UTF_8;
	}
}