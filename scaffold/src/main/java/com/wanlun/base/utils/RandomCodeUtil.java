package com.wanlun.base.utils;

import java.util.Random;

/**
 * 获取随机数(短信验证码)工具类
 */
public class RandomCodeUtil {

	private static final Random random = new Random();

	/**	 生成4位验证码	*/
	public static String getFourBitRandom() {
		String code = "";
		for (int i = 1; i <= 4; i++) {
			int num = ((int) (Math.random()*9)) + 1;
			code += num;
		}
		return code;
	}

	/**	 生成6位验证码	*/
	public static String getSixBitRandom() {
		String code = "";
		for (int i = 1; i <= 6; i++) {
			//不能含0，因为阿里云短信会自动去掉0
			int num = ((int) (Math.random()*9)) + 1;
			code += num;
		}
		return code;
	}

}
