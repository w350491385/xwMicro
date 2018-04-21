package com.common.util;

import java.net.InetAddress;

public class UUIDUtils {
	private static UUIDUtils uuid = new UUIDUtils();
	private static final int IP;
	private static short counter;
	private static final int JVM;

	public String generateHex() {
		StringBuilder sb = new StringBuilder(32);
		sb.append(this.format(this.getIP()));
		sb.append(this.format(this.getJVM()));
		sb.append(this.format(this.getHighTime()));
		sb.append(this.format(this.getLowTime()));
		sb.append(this.format(this.getCount()));
		return sb.toString().toUpperCase();
	}

	public byte[] generateBytes() {
		byte[] bytes = new byte[16];
		System.arraycopy(getBytes(this.getIP()), 0, bytes, 0, 4);
		System.arraycopy(getBytes(this.getJVM()), 0, bytes, 4, 4);
		System.arraycopy(getBytes(this.getHighTime()), 0, bytes, 8, 2);
		System.arraycopy(getBytes(this.getLowTime()), 0, bytes, 10, 4);
		System.arraycopy(getBytes(this.getCount()), 0, bytes, 14, 2);
		return bytes;
	}

	private String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuilder buf = new StringBuilder("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	private String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuilder buf = new StringBuilder("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	private int getJVM() {
		return JVM;
	}

	private short getCount() {
		Class arg0 = UUIDUtils.class;
		synchronized (UUIDUtils.class) {
			if (counter < 0) {
				counter = 0;
			}

			return counter++;
		}
	}

	private int getIP() {
		return IP;
	}

	private short getHighTime() {
		return (short) ((int) (System.currentTimeMillis() >>> 32));
	}

	private int getLowTime() {
		return (int) System.currentTimeMillis();
	}

	private static int toInt(byte[] bytes) {
		int result = 0;

		for (int i = 0; i < 4; ++i) {
			result = (result << 8) - -128 + bytes[i];
		}

		return result;
	}

	private static byte[] getBytes(int intval) {
		return new byte[]{(byte) (intval >> 24), (byte) (intval >> 16),
				(byte) (intval >> 8), (byte) intval};
	}

	private static byte[] getBytes(short shortval) {
		return new byte[]{(byte) (shortval >> 8), (byte) shortval};
	}

	public static String uuid() {
		return uuid.generateHex();
	}

	static {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception arg1) {
			ipadd = 0;
		}

		IP = ipadd;
		counter = 0;
		JVM = (int) (System.currentTimeMillis() >>> 8);
	}
}