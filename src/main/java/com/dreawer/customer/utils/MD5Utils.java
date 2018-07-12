package com.dreawer.customer.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <code>MD5Utils</code> <code>MD5</code> 加密工具类。
 * </br>
 * 该类为应用程序提供信息摘要算法的基本方法，同时还可以指定密钥进行二次加密，
 * 以及指定加密位数，但仅支持16位和32位，指定此外的任何位数都将进行32位加密。
 * @author David Dai
 * @since Dream-Util 1.0
 * @version 1.0
 */
public class MD5Utils {
	
	/**
	 * 32 位加密，默认为 32 位加密。
	 */
	public static final int BIT_32 = 32;
	
	/**
	 * 16 位加密
	 */
	public static final int BIT_16 = 16;
	
	/**
	 * 对指定字符串进行 <code>MD5</code> 加密处理，并获取32位的摘要信息。
	 * </br>
	 * 在为关键信息加密时推荐使用 {@link #encrypt(String, String)} 方法，该方法进行二次加密，可有效避免穷举破解。
	 * <pre>
	 * String pwd = "000000";
	 * MD5Utils.encrypt(pwd);
	 * </pre>
	 * @param str 待加密的字符串。
	 * @return 加密后的摘要信息。
	 * @author David Dai
	 * @since 1.0
	 */
	public static String encrypt(String str) {
		return encrypt(str, MD5Utils.BIT_32);
	}
	
	/**
	 * 对指定字符串进行 <code>MD5</code> 加密处理，并获取指定位数的摘要信息。
	 * </br>
	 * 在为关键信息加密时推荐使用 {@link #encrypt(String, String, int)} 方法，该方法进行二次加密，可有效避免穷举破解。
	 * <pre>
	 * String pwd = "000000";
	 * MD5Utils.encrypt(pwd, MD5Utils.BIT_16);
	 * 或
	 * MD5Utils.encrypt(pwd, MD5Utils.BIT_32);
	 * </pre>
	 * @param str 待加密的字符串。
	 * @param bit 待加密的位数。有效值为：{@value #BIT_16}位和{@value #BIT_32}位，默认为：{@value #BIT_32}位。
	 * @return 加密后的摘要信息。
	 * @author David Dai
     * @since 1.0
	 */
	public static String encrypt(String str, int bit) {
		return encrypt(str, null, bit);
	}
	
	/**
	 * 对指定字符串以密钥方式进行 <code>MD5</code> 加密处理，并获取32位的摘要信息。
	 * </br>
	 * 该方法是对字符串加密后的摘要信息进行带密钥的二次加密，与简单的二次加密相比更加安全，可以充分避免穷举破解。
	 * <pre>
	 * String pwd = "000000";
	 * String key = "key";
	 * MD5Utils.encrypt(pwd, key);
	 * </pre>
	 * @param str 待加密的字符串。
	 * @param key 加密时使用的密钥。
	 * @return 加密后的摘要信息。
	 * @author David Dai
     * @since 1.0
	 */
	public static String encrypt(String str, String key) {
		return encrypt(str, key, MD5Utils.BIT_32);
	}
	
	/**
	 * 对指定字符串以密钥方式进行 <code>MD5</code> 加密处理，并获取指定位数的摘要信息。
	 * </br>
	 * 该方法是对字符串加密后的摘要信息进行带密钥的二次加密，与简单的二次加密相比更加安全，可以充分避免穷举破解。
	 * <pre>
	 * String pwd = "000000";
	 * String key = "key";
	 * MD5Utils.encrypt(pwd, key, MD5Utils.BIT_16);
	 * 或
	 * MD5Utils.encrypt(pwd, key, MD5Utils.BIT_32);
	 * </pre>
	 * @param str 待加密的字符串。
	 * @param key 加密时使用的密钥。
	 * @param bit 待加密的位数。有效值为：{@value #BIT_16}位、{@value #BIT_32}位，默认为：{@value #BIT_32}位。
	 * @return 加密后的摘要信息。
	 * @author David Dai
     * @since 1.0
	 */
	public static String encrypt(String str, String key, int bit) {
		String md5Code = encrypt(str.getBytes(), bit);
		if (key != null) {
			md5Code = encrypt(md5Code + key, bit);
		}
		return md5Code;
	}
	
	/**
	 * 对指定字符数组进行 <code>MD5</code> 加密处理，并获取32位摘要信息。
	 * </br>
	 * 在为关键信息加密时推荐使用 {@link #encrypt(byte[], String)} 方法，该方法进行二次加密，可有效避免穷举破解。
	 * <pre>
	 * byte[] pwd = {'0', '0', '0', '0', '0', '0'}; 
	 * MD5Utils.encrypt(pwd);
	 * </pre>
	 * @param bytes 需要进行加密处理的字符数组。
	 * @return 加密处理后的摘要信息。
	 * @author David Dai
     * @since 1.0
	 */
	public static String encrypt(byte[] bytes) {
		return encrypt(bytes, MD5Utils.BIT_32);
	}
	
	/**
	 * 对指定字符数组进行 <code>MD5</code> 加密处理，并获取指定位数摘要信息。
	 * </br>
	 * 在为关键信息加密时推荐使用 {@link #encrypt(byte[], String, int)} 方法，该方法进行二次加密，可有效避免穷举破解。
	 * <pre>
	 * byte[] pwd = {'0', '0', '0', '0', '0', '0'};
	 * MD5Utils.encrypt(pwd, MD5Utils.BIT_16);
	 * 或
	 * MD5Utils.encrypt(pwd, MD5Utils.BIT_32);
	 * </pre>
	 * @param bytes 需要进行加密处理的字符数组。
	 * @param bit 待加密的位数。有效值为：{@value #BIT_16}位和{@value #BIT_32}位，默认为：{@value #BIT_32}位。
	 * @return 加密处理后的摘要信息。
	 * @author David Dai
     * @since 1.0
	 */
	public static String encrypt(byte[] bytes, int bit) {
		byte[] digests = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(bytes); // 使用指定的 byte 数组更新摘要
			digests = digest.digest(); // 按指定加密方式进行哈希计算
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String md5Code = byte2hex(digests);
		return bit == MD5Utils.BIT_16 ? md5Code.substring(8, 24) : md5Code;
	}
	
	/**
	 * 对指定字符数组以密钥方式进行 <code>MD5</code> 加密处理，并获取32位摘要信息。
	 * </br>
	 * 该方法是对字符串加密后的摘要信息进行带密钥的二次加密，与简单的二次加密相比更加安全，可以充分避免穷举破解。
	 * <pre>
	 * byte[] pwd = {'0', '0', '0', '0', '0', '0'};
	 * String key = "key";
	 * MD5Utils.encrypt(pwd, key);
	 * </pre>
	 * @param bytes 需要进行加密处理的字符数组。
	 * @param key 加密时使用的密钥。
	 * @return 加密处理后的摘要信息。
	 * @author David Dai
     * @since 1.0
	 */
	public static String encrypt(byte[] bytes, String key) {
		return encrypt(bytes, key, MD5Utils.BIT_32);
	}
	
	/**
	 * 对指定字符数组以密钥方式进行 <code>MD5</code> 加密处理，并获取指定位数摘要信息。
	 * </br>
	 * 该方法是对字符串加密后的摘要信息进行带密钥的二次加密，与简单的二次加密相比更加安全，可以充分避免穷举破解。
	 * <pre>
	 * byte[] pwd = {'0', '0', '0', '0', '0', '0'};
	 * String key = "key";
	 * MD5Utils.encrypt(pwd, key, MD5Utils.BIT_16);
	 * 或
	 * MD5Utils.encrypt(pwd, key, MD5Utils.BIT_32);
	 * </pre>
	 * @param bytes 需要进行加密处理的字符数组。
	 * @param key 加密时使用的密钥。
	 * @param bit 待加密的位数。有效值为：{@value #BIT_16}位和{@value #BIT_32}位，默认为：{@value #BIT_32}位。
	 * @return 加密处理后的摘要信息。
	 * @author David Dai
     * @since 1.0
	 */
	public static String encrypt(byte[] bytes, String key, int bit) {
		String md5Code = encrypt(bytes, bit);
		return encrypt(md5Code + key, bit);
	}
	
	/**
	 * 摘要信息由数组转换为字符串。
	 * @param bytes 需要转换为字符串的摘要信息。
	 * @return 返回转换后全大写的摘要信息。
	 * @author David Dai
     * @since 1.0
	 */
	private static String byte2hex(byte[] bytes) {
		String stmp = new String();
		StringBuffer hex = new StringBuffer();
		for (byte b : bytes) {
			stmp = Integer.toHexString(b & 0XFF);
			if (stmp.length() == 1) {
				hex = hex.append("0");
			}
			hex.append(stmp);
		}
		return hex.toString().toUpperCase();
	}
	
	/**
	 * 验证指定密码串。
	 * <pre>
	 * String pwdStr = "000000";
	 * String md5Str = "670B14728AD9902AECBA32E22FA4F6BD";
	 * MD5Utils.verifyPassword(pwdStr, md5Str);
	 * </pre>
	 * @param pwdStr 待验证的密码串。
	 * @param md5Str 作为验证标准的 <code>MD5</code> 字符串。
	 * @return 验证结果。如果相等则返回 <code>true</code> 否则返回 <code>false</code>。
	 * @author David Dai
     * @since 1.0
	 */
	public static boolean verifyPassword(String pwdStr, String md5Str) {
		return encrypt(pwdStr, MD5Utils.BIT_32).equals(md5Str);
	}
	
	/**
	 * 验证指定密码串。
	 * <pre>
	 * String pwdStr = "000000";
	 * 
	 * String md5Str = "8AD9902AECBA32E2";
	 * MD5Utils.verifyPassword(pwdStr, md5Str, MD5Utils.BIT_16);
	 * 或
	 * String md5Str = "670B14728AD9902AECBA32E22FA4F6BD";
	 * MD5Utils.verifyPassword(pwdStr, md5Str, MD5Utils.BIT_32);
	 * </pre>
	 * @param pwdStr 待验证的密码串。
	 * @param md5Str 作为验证标准的 <code>MD5</code> 字符串。
	 * @param bit 待加密的位数。有效值为：{@value #BIT_16}位和{@value #BIT_32}位，默认为：{@value #BIT_32}位。
	 * @return 验证结果。如果相等则返回 <code>true</code> 否则返回 <code>false</code>。
	 * @author David Dai
     * @since 1.0
	 */
	public static boolean verifyPassword(String pwdStr, String md5Str, int bit) {
		return encrypt(pwdStr, bit).equals(md5Str);
	}
	
	/**
	 * 根据指定密钥验证密码串。
	 * <pre>
	 * String pwdStr = "000000";
	 * String md5Str = "670B14728AD9902AECBA32E22FA4F6BD";
	 * String key = "key";
	 * MD5Utils.verifyPassword(pwdStr, md5Str, key);
	 * </pre>
	 * @param pwdStr 待验证的密码串。
	 * @param md5Str 作为验证标准的 <code>MD5</code> 字符串。
	 * @param key 验证时使用的密钥。
	 * @return 验证结果。如果相等则返回 <code>true</code> 否则返回 <code>false</code>。
	 * @author David Dai
     * @since 1.0
	 */
	public static boolean verifyPassword(String pwdStr, String md5Str, String key) {
		return encrypt(pwdStr, key, MD5Utils.BIT_32).equals(md5Str);
	}
	
	/**
	 * 根据指定密钥验证密码串。
	 * <pre>
	 * String pwdStr = "000000";
	 * String key = "key";
	 * 
	 * String md5Str = "8AD9902AECBA32E2";
	 * MD5Utils.verifyPassword(pwdStr, md5Str, key, MD5Utils.BIT_16);
	 * 或
	 * String md5Str = "670B14728AD9902AECBA32E22FA4F6BD";
	 * MD5Utils.verifyPassword(pwdStr, md5Str, key, MD5Utils.BIT_32);
	 * </pre>
	 * @param pwdStr 待验证的密码串。
	 * @param md5Str 作为验证标准的 <code>MD5</code> 字符串。
	 * @param key 验证时使用的密钥。
	 * @param bit 待加密的位数。有效值为：{@value #BIT_16}位和{@value #BIT_32}位，默认为：{@value #BIT_32}位。
	 * @return 验证结果。如果相等则返回 <code>true</code> 否则返回 <code>false</code>。
	 * @author David Dai
     * @since 1.0
	 */
	public static boolean verifyPassword(String pwdStr, String md5Str, String key, int bit) {
		return encrypt(pwdStr, key, bit).equals(md5Str);
	}
	
}
