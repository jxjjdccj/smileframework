package org.smileframework.common.jdk;

import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/**
 * DES加密解密工具类
 */
public abstract class EncryptUtil {

    private static final String PASSWORD_CRYPT_KEY = "_@dc4BOC";//密钥
    private final static String DES = "DES";

    /**
     * 加密
     * @param src 数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回加密后的数据
     * @throws Exception
     */
    private static byte[] encrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        // 现在，获取数据并加密
        // 正式执行加密操作
        return cipher.doFinal(src);
    }

    /**
     * 解密
     * @param src --数据源
     * @param key 密钥，长度必须是8的倍数
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    private static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 现在，获取数据并解密
        // 正式执行解密操作
        return cipher.doFinal(src);
    }

    /**
     * 密码解密
     * @param data --解密数据
     * @return
     * @throws Exception
     */
    public final static String decrypt(String data) {
        try {
            byte[] cipherBytes = hex2byte(data);
            return new String(decrypt(cipherBytes, PASSWORD_CRYPT_KEY.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 密码加密
     * @param data  加密数据 
     * @return  加密后的字符串
     * @throws Exception
     */
    public final static String encrypt(String data) {
        try {
            byte[] cipherBytes = encrypt(data.getBytes(), PASSWORD_CRYPT_KEY.getBytes());
            return byte2hex(cipherBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 二进制字节转换为16进制表示，以便存入到介质中
     * @param data
     * @return
     */
    private final static String byte2hex(byte[] data) {

        String str = "";
        String tmp = "";

        for (int i = 0; i < data.length; i++) {

            tmp = Integer.toHexString(data[i] & 0xFF);

            if (tmp.length() == 1) {
                str += "0" + tmp;
            } else {
                str += tmp;
            }
        }
        return str;
    }

    /**
     * 16进制转换为二进制形式字节，用以解密
     * @param data
     * @return
     */
    private final static byte[] hex2byte(String data) {

        char[] chData = data.toCharArray();

        byte[] byteData = new byte[chData.length / 2];

        for (int i = 0; i < chData.length; i += 2) {

            byte bHi = hexCh2Byte(chData[i]);
            byte bLow = hexCh2Byte(chData[i + 1]);
            byte bWord = (byte) ((bHi << 4) | bLow);

            byteData[i / 2] = bWord;
        }

        return byteData;
    }

    /**
     * 某个16进制数字字符转换成相应的0xXX形式
     * @param hexCh
     * @return
     */
    private final static byte hexCh2Byte(char hexCh) {
        byte bResult = 0;

        switch (hexCh) {
            case '0': {
                bResult = 0x00;
                break;
            }
            case '1': {
                bResult = 0x01;
                break;
            }
            case '2': {
                bResult = 0x02;
                break;
            }
            case '3': {
                bResult = 0x03;
                break;
            }
            case '4': {
                bResult = 0x04;
                break;
            }
            case '5': {
                bResult = 0x05;
                break;
            }
            case '6': {
                bResult = 0x06;
                break;
            }
            case '7': {
                bResult = 0x07;
                break;
            }
            case '8': {
                bResult = 0x08;
                break;
            }
            case '9': {
                bResult = 0x09;
                break;
            }
            case 'a':
            case 'A': {
                bResult = 0x0A;
                break;
            }
            case 'b':
            case 'B': {
                bResult = 0x0B;
                break;
            }
            case 'c':
            case 'C': {
                bResult = 0x0C;
                break;
            }
            case 'd':
            case 'D': {
                bResult = 0x0D;
                break;
            }
            case 'e':
            case 'E': {
                bResult = 0x0E;
                break;
            }
            case 'f':
            case 'F': {
                bResult = 0x0F;
                break;
            }
            default:
        }
        return bResult;
    } 
}