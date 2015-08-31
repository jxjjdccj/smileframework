package org.smileframework.common.jdk;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class OtherUtil {

	private static Random rand = new Random(System.currentTimeMillis());
	private static String localHostname = "";

	/**
	 * 删除文件名中的路径，只返回文件名
	 * 
	 * @param filename
	 * @return
	 */
	public final static String removePath(String filename) {
		int i = filename.lastIndexOf('/');
		if (i == -1) {
			return filename;
		} else {
			return filename.substring(i + 1);
		}
	}

	/**
	 * 文件最后一次被修改的时间
	 * 
	 * 
	 * @param filename
	 * @return 0-表示文件不存在或无权限读取，大于0为修改时间
	 */
	public static long getFileLastModified(String filename) {
		long l = 0;
		File f = new File(filename);
		if (f.exists()) {
			try {
				l = f.lastModified();
			} catch (SecurityException se) {
				l = 0;
				se.printStackTrace();
			}
		}
		return l;
	}

	/**
	 * 获取本机名称
	 * 
	 * @return
	 */
	public static String getLocalHostName() {
		if (localHostname.equals("")) {
			try {
				localHostname = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				localHostname = "UnknownHost";
			}
		}
		return localHostname;
	}

	private static String localHostIps = "";

	/**
	 * 获取本地所有的ip地址，组成字符串返回（以“,”分隔）
	 * 
	 * @return
	 */
	public static String getLocalHostIps() {
		if (localHostIps.equals("")) {
			StringBuffer sb = new StringBuffer();
			InetAddress[] ias;
			try {
				ias = InetAddress.getAllByName(InetAddress.getLocalHost()
						.getHostName());
				for (int i = 0; i < ias.length; i++) {
					String ip = ias[i].getHostAddress();
					sb.append(ip);
					sb.append(",");
				}
				localHostIps = sb.toString();
			} catch (UnknownHostException e) {
				localHostIps = "";
			}
		}
		return localHostIps;
	}

	public static Random getRandom() {
		return rand;
		// return new Random(System.currentTimeMillis());
	}

	/**
	 * Generates pseudo-random long from specific range. Generated number is
	 * great or equals to min parameter value and less then max parameter value.
	 * 
	 * @param min
	 *            lower (inclusive) boundary
	 * @param max
	 *            higher (exclusive) boundary
	 * 
	 * @return pseudo-random value
	 */

	public static long randomLong(long min, long max) {
		return min + (long) (Math.random() * (max - min));
	}

	/**
	 * 清空数组
	 * 
	 * @param arg
	 */
	public static void clearArray(Object arg[]) {
		if (arg == null) {
			return;
		}
		for (int i = 0; i < arg.length; i++) {
			arg[i] = null;
		}
		arg = null;
	}

	/**
	 * Generates pseudo-random integer from specific range. Generated number is
	 * great or equals to min parameter value and less then max parameter value.
	 * 
	 * @param min
	 *            lower (inclusive) boundary
	 * @param max
	 *            higher (exclusive) boundary
	 * 
	 * @return pseudo-random value
	 */
	public static int randomInt(int min, int max) {
		return min + (int) (Math.random() * (max - min));
	}

	/**
	 * 返回此对象序列化后的大小（字节数）
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	public final static int sizeOf(Object obj) {
		ByteArrayOutputStream bao = null;
		ObjectOutputStream oos = null;
		try {
			bao = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bao);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			return bao.size();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (bao != null) {
					bao.close();
					bao = null;
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 中断线程一段时间，可代替sleep，采取object.wait实现
	 * 
	 * @param lockObj对象锁
	 * @param sometime中断时间
	 *            ，单位毫秒
	 * @throws InterruptedException
	 */
	public static final void blockSomeTime(Object lockObj, long sometime)
			throws InterruptedException {
		if (Thread.interrupted())
			throw new InterruptedException();
		synchronized (lockObj) {
			long waitTime = sometime;
			long start = System.currentTimeMillis();
			try {
				for (;;) {
					lockObj.wait(waitTime);
					waitTime = sometime - (System.currentTimeMillis() - start);
					if (waitTime <= 0) {
						break;
					}
				}
			} catch (InterruptedException ex) {
				lockObj.notify();
				throw ex;
			}
		}
	}

	/**
	 * 对象转换成字节数组
	 * 
	 * 
	 * @param obj
	 * @return
	 */
	public final static byte[] objToBytes(Object obj) {
		ByteArrayOutputStream bao = null;
		ObjectOutputStream oos = null;
		try {
			bao = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bao);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			return bao.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (bao != null) {
					bao.close();
					bao = null;
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 字节数组转成对象
	 * 
	 * @param bytes
	 * @return
	 */
	public final static Object bytesToObj(byte[] bytes) {
		ByteArrayInputStream bai = null;
		ObjectInputStream ois = null;
		try {
			bai = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bai);
			Object obj = ois.readObject();
			ois.close();
			ois = null;
			return obj;
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		} finally {
			try {
				if (bai != null) {
					bai.close();
					bai = null;
				}
			} catch (IOException e) {
			}
		}
	}

	/**
	 * 检查ip地址的合法性
	 * 
	 * 
	 * @param ip
	 * @return
	 */
	public final static boolean checkip(String ip) {
		Pattern patt = Pattern
				.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		Matcher mat = patt.matcher(ip);
		return mat.matches();
	}

	/**
	 * 获取一个dns或计算机名称所对应的ip地址数组
	 * 
	 * @param dnsip
	 * @return
	 */
	public final static String[] getDnsIPs(String dnsip) {
		String ips[] = null;
		try {
			InetAddress ias[] = InetAddress.getAllByName(dnsip);
			ips = new String[ias.length];
			for (int i = 0; i < ias.length; i++) {
				ips[i] = ias[i].getHostAddress();
				ias[i] = null;
			}
		} catch (UnknownHostException e) {
			ips = null;
		}
		return ips;
	}

	/**
	 * 对象深度克隆
	 * 
	 * @param originObj
	 * @return
	 */
	public final static Object objectClone(Object originObj) {
		ByteArrayOutputStream bao = null;
		ByteArrayInputStream bai = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try {
			bao = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bao);
			oos.writeObject(originObj);
			oos.flush();
			oos.close();
			bai = new ByteArrayInputStream(bao.toByteArray());
			ois = new ObjectInputStream(bai);
			Object obj = ois.readObject();
			ois.close();
			oos = null;
			ois = null;
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (bao != null) {
					bao.close();
					bao = null;
				}
				if (bai != null) {
					bai.close();
					bai = null;
				}
			} catch (IOException e) {
			}
		}
	}
}
