package com.fastfreesky.www.tool.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName: TimeDiff
 * @Description: TODO(这里用一句话描述这个类的作用)该类用于计算时间差,用于统计某个任务的耗时时间
 * @author tianyu.yang
 * @date 2013-8-2 上午11:30:34
 * 
 */
public class TimeDiff {

	private String startTime;
	private String endTime;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	private static SimpleDateFormat dfs = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	private String getCurrentTime() {
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		return dfs.format(curDate);
	}

	public void Start() {
		setStartTime(getCurrentTime());
	}

	public void End() {
		setEndTime(getCurrentTime());
		getBetweenTime(getStartTime(), getEndTime());		
	}

	public void printTimeDiff() {
		
		System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒" + ms
				+ "毫秒");
	}

	private void getBetweenTime(String start, String end) {
		long between = 0;
		try {
			Date begin = dfs.parse(start);
			Date ends = dfs.parse(end);
			between = (ends.getTime() - begin.getTime());// 得到两者的毫秒数
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		setDay(between / (24 * 60 * 60 * 1000));
		setHour(between / (60 * 60 * 1000) - day * 24);
		setMin((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		setS(between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		setMs(between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min
				* 60 * 1000 - s * 1000);

	}

	public long getDay() {
		return day;
	}

	public void setDay(long day) {
		this.day = day;
	}

	public long getHour() {
		return hour;
	}

	public void setHour(long hour) {
		this.hour = hour;
	}

	public long getMin() {
		return min;
	}

	public void setMin(long min) {
		this.min = min;
	}

	public long getS() {
		return s;
	}

	public void setS(long s) {
		this.s = s;
	}

	public long getMs() {
		return ms;
	}

	public void setMs(long ms) {
		this.ms = ms;
	}

	private long day;
	private long hour;
	private long min;
	private long s;
	private long ms;
	
	public static void main(String[] args) {
		TimeDiff diff = new TimeDiff();
		diff.Start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		diff.End();
		diff.printTimeDiff();
	}
}
