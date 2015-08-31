package org.smileframework.common.jdk;

import java.net.InetAddress;
/**
 * UUID 生成工具、用于插表时生成唯一的ID工具
 * @author cz
 */
public abstract class UidUtil {
	private static final String sep = "";

	private static final int IP;

	private static short counter = (short) 0;

	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);


	static {

		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}


	public static int toInt(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + bytes[i];
		}
		return result;
	}

	private static String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	private static String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	private static int getJVM() {
		return JVM;
	}

	private static synchronized short getCount() {
		if (counter < 0) {
			counter = 0;
		}
		return counter++;
	}

	private static int getIP() {
		return IP;
	}

	private static short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	private static int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	public static String generate() {
		return new StringBuffer(36).append(format(getIP())).append(sep).append(
				format(getJVM())).append(sep).append(format(getHiTime()))
				.append(sep).append(format(getLoTime())).append(sep).append(
						format(getCount())).toString();
	}
}