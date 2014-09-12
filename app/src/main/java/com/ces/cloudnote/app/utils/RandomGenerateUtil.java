package com.ces.cloudnote.app.utils;

import java.util.Random;

/**
 * 生成6位随机密码,包含3位字母,3位数字
 * 复制，可以直接使用了。。。
 * */
public class RandomGenerateUtil {
	private static Random getR = new Random();

	public static String getRandomS() {
		RandomGenerateUtil p = new RandomGenerateUtil();
		String s = "";
		int n = 0;
		int m = 0;
		for (int i = 0; i < 6; i++) {
			if (n == 3) {
				s += p.getSz();
				m++;
			} else if (m == 3) {
				s += p.getZm();
				n++;
			} else {
				int ri = getR.nextInt(2);
				s += ri == 0 ? p.getSz() : p.getZm();
			}
		}
		return s;
	}

	// 随机数字的字母，区分大小写
	private String getZm() {
		char sSS = (char) (getR.nextInt(26) + 97);// 小写字母97--122=a---z
		char sBs = (char) (getR.nextInt(26) + 65);// 大写65--90=A----Z
		char[] stemp = { sSS, sBs };
		int i = getR.nextInt(2);
		String sS = String.valueOf(stemp[i]);
		return sS;
	}

	// 随机数字的字符串
	private String getSz() {
		int getI = getR.nextInt(10) + 48;// 数字48--57=0---9
		String sI = String.valueOf((char) getI);
		return sI;
	}
	
	/*public static void main(String[] args) {
		String print = getRandomS();
		System.out.println(print);
	}*/
}