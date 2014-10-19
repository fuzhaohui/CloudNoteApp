package com.ces.cloudnote.app.utils;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	/**
	 * 将原文加密得到字符串
	 * @param content
	 * @param password
	 * @return
	 */
	public static String encryptContent2HexStr(String content, String password){
		byte[] encryptResult;
		try {
			encryptResult = encrypt(content.getBytes("utf-8"), password);
			String encryptResultStr = parseByte2HexStr(encryptResult); 
			return encryptResultStr;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
		return null;
	}
	
	/**
	 * 解密字符串得到原文
	 * @param hexStr
	 * @param password
	 * @return
	 */
	public static String decryptHexStr2Content(String hexStr, String password){
		byte[] decryptFrom = parseHexStr2Byte(hexStr);  
		byte[] decryptResult = decrypt(decryptFrom,password);  
		String content = new String(decryptResult); 
		return content;
	}
	
	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	private static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase(Locale.ENGLISH));
		}
		return sb.toString();
	}

	/**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
	
	/**
	 * 加密byte[]
	 * @param input
	 * @param keyStr
	 * @return
	 */
	private static byte[] encrypt( byte[] input,String keyStr) {
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		try {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(keyStr.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);//
			byte[] result = cipher.doFinal(input);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密 byte[]
	 * @param input  待解密内容
	 * @param keyStr 解密密钥
	 * @return 
	 */
	private static byte[] decrypt(byte[] input, String keyStr) {
		byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		try {
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			SecretKeySpec key = new SecretKeySpec(keyStr.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);//
			byte[] result = cipher.doFinal(input);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
