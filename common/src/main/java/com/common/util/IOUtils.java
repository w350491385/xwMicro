package com.common.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public abstract class IOUtils {
	private static final int EOF = -1;
	private static final int DEFAULT_BUFFER_SIZE = 4096;

	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException arg1) {
			;
		}

	}

	public static void write(String data, OutputStream output, String encoding)
			throws IOException {
		if (data != null) {
			output.write(data.getBytes(Charsets.toCharset(encoding)));
		}

	}

	public static String toString(InputStream input, String encoding)
			throws IOException {
		InputStreamReader in = new InputStreamReader(input,
				Charsets.toCharset(encoding));
		boolean n = false;
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[4096];

		int n1;
		while (-1 != (n1 = in.read(buffer))) {
			if (null != buffer) {
				builder.append(buffer, 0, n1);
			}
		}

		return builder.toString();
	}

	public static byte[] toByteArray(InputStream input, long longSize)
			throws IOException {
		if (longSize > 2147483647L) {
			throw new IllegalArgumentException("流的期望长度不能超过int能表示的范围，当前长度： "
					+ longSize);
		} else {
			int size = (int) longSize;
			if (size < 0) {
				throw new IllegalArgumentException("流的期望长度必须大于等于0，当前长度： "
						+ size);
			} else if (size == 0) {
				return new byte[0];
			} else {
				byte[] data = new byte[size];

				int offset;
				int readed;
				for (offset = 0; offset < size
						&& (readed = input.read(data, offset, size - offset)) != -1; offset += readed) {
					;
				}

				if (offset != size) {
					throw new IOException("实际读取的流的长度和期望的长度不一致，实际读取长度：" + offset
							+ ", 期望长度: " + size);
				} else {
					return data;
				}
			}
		}
	}
}