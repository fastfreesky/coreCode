package com.fastfreesky.www.tool.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName: Time
 * @Description: TODO(这里用一句话描述这个类的作用)常用的一些time类的函数
 * @author tianyu.yang
 * @date 2013-8-19 下午5:29:14
 * 
 */
public class Time {

	/**
	 * 默认的时间格式
	 */
	private static SimpleDateFormat dfs = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static void setTimeFormat(String format) {
		dfs = new SimpleDateFormat(format);
	}

	public static String getCurrentTime() {
		return dfs.format(new Date(System.currentTimeMillis()));
	}

	public static String getCurrentTime(String format) {
		return new SimpleDateFormat(format).format(new Date(System
				.currentTimeMillis()));
	}

	public static void main(String[] args) {
		System.out.println(getCurrentTime());
		setTimeFormat("yyyyMMdd");
		System.out.println(getCurrentTime());
		System.out.println(getCurrentTime("yyyyMMdd"));
	}

}
