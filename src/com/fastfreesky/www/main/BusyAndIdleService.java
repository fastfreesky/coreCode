package com.fastfreesky.www.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BusyAndIdleService {

	/**
	 * 处理查询出来的原始数据
	 * 
	 * @author chengpengfei
	 * @date 2013-05-26
	 * @param map
	 *            数据库原始记录
	 * @param funcType
	 *            统计方式, 'times' -- 按下载次数统计; 'speed' -- 按下载速度统计
	 * @return List 结果集 [{date: '', busystart: '' ...},...]
	 */
	private static List handleData(Map map, String funcType) {

		Long totals = 0l;
		Double floatss = 0d;
		Long down_nums = 0l;
		Double down_speeds = 0d;

		List listResult = null;
		Map mapResult = null;
		if (map != null && map.size() > 0) {// 当列表不为空时
			listResult = new ArrayList();
			// 循环迭代每一天的数据：
			Iterator<Map.Entry> it = map.entrySet().iterator();
			// 在返回列表的的map中增加sort项，以便于前台进行排序：
			int sort = 0;
			while (it.hasNext()) {
				Map.Entry entry = it.next();
				String keyDate = String.valueOf(entry.getKey());
				List<InputType> listDate = (List) entry.getValue();

				if (listDate != null && listDate.size() > 0) {
					// 初始化每一天数据
					mapResult = new HashMap();
					// 计算出当天需要迭代的时间点区间：
					Date endDayDate = null;
					try {
						endDayDate = new Date(new SimpleDateFormat("yyyyMMdd")
								.parse(keyDate).getTime() + 276 * 5 * 60 * 1000);// 转化成日期格式:
																					// 276为288
																					// -
																					// 12
					} catch (ParseException e) {
						e.printStackTrace();
					}
					// 返回参数列表
					String date = keyDate; // 日期
					String busystart = null;// 忙时开始
					String busyend = null;// 忙时结束
					String idlestart = null;// 闲时开始
					String idleend = null;// 闲时结束
					double busyspeed = 0;// 忙时速度
					double idlespeed = 0;// 闲时速度
					double rate = 0;// 忙闲时比率

					long timesmany = 0;// 最大下载数
					long timesfew = 0;// 最小下载数

					DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
					// 迭代一天中每5分钟的数据：
					int len = listDate.size();
					for (int i = 0; i < len; i++) {
						InputType recordMap = listDate.get(i);// 当前的一个小时内的起始数据

						totals += recordMap.getTotal();
						floatss += recordMap.getFloats();
						down_nums += recordMap.getDown_num();
						down_speeds += recordMap.getDown_speed();

						String beginDateStr = recordMap.getDate();// 当前的一个小时的起始日期:
						// yyyy-MM-dd
						String beginTimeStr = recordMap.getTime();// 当前的一个小时的起始时间:
						// HH:mm:ss
						String endTimeStr = null;// 当前一个小时的结束时间字符串
						Date beginHourDate = null;// 当前的一个小时开始时间的日期Date对象yyyy-MM-dd
													// HH:mm:ss
						Date endHourDate = null;// 当前的一个小时结束时间的日期Date对象yyyy-MM-dd
												// HH:mm:ss
						try {
							beginHourDate = df.parse(beginDateStr + " "
									+ beginTimeStr);// 转化成日期格式
						} catch (ParseException e) {
							e.printStackTrace();
						}
						// 决断是否已经迭代到最后一个小时，如果迭代到最后一个小时 ，就结束
						if (beginHourDate.equals(endDayDate)
								|| beginHourDate.after(endDayDate)) {
							break;
						}
						endHourDate = new Date(
								beginHourDate.getTime() + 60 * 60 * 1000);
						// 得到结束时间字符串
						Calendar c = Calendar.getInstance();
						c.setTime(endHourDate);
						endTimeStr = (c.get(Calendar.HOUR_OF_DAY) < 10 ? "0"
								+ c.get(Calendar.HOUR_OF_DAY) : c
								.get(Calendar.HOUR_OF_DAY))
								+ ":"
								+ (c.get(Calendar.MINUTE) < 10 ? "0"
										+ c.get(Calendar.MINUTE) : c
										.get(Calendar.MINUTE));
						// 存放一个小时的临时值：
						double speeds = 0;// 速度和
						long total = 0;
						long down_num = 0;
						double perspeed = 0;
						// 开始循环当前的一个小时
						int loop = i;
						while (loop - i < 12 && loop < listDate.size()) {// 最大是12：中间可能会漏掉部分数据
							InputType hourMap = (InputType) listDate.get(loop);
							String beginDateHourStr = hourMap.getDate();
							String beginTimeHourStr = hourMap.getTime();
							Date currHourDate = null;// 当前的一个五分钟的起始时间
							try {
								currHourDate = df.parse(beginDateHourStr + " "
										+ beginTimeHourStr);// 转化成日期格式
							} catch (ParseException e) {
								e.printStackTrace();
							}
							if (currHourDate.before(endHourDate)) {// 判断当前时间是否在本循环的一个小时之内
								speeds += (Double) hourMap.getDown_speed();
								total += (Long) hourMap.getTotal();
								down_num += (Long) hourMap.getDown_num();
							} else {
								break;
							}
							loop++;
						}
						// 判断忙闲时
						if (down_num != 0) {// 有效的总下载次数
							perspeed = speeds / down_num;
							if (busyspeed == 0 || perspeed < busyspeed) {// 忙时：下载速度小的时候为忙时。
								busyspeed = perspeed;
								busystart = beginTimeStr;
								busyend = endTimeStr;
							}
							if (perspeed > idlespeed) {// 闲时：下载速度大的时候为闲时。
								idlespeed = perspeed;
								idlestart = beginTimeStr;
								idleend = endTimeStr;
							}
						}
						// 注：上面的算法，不使用if-else是由于第一次的时候，没有可比较对象，所以既是最大值又是最小值。
					}// end of for listDate
						// 计算忙闲时速度比
					try {
						rate = busyspeed / idlespeed;
					} catch (RuntimeException e) {
						e.printStackTrace();
						rate = 0;
					}
					// 格式化结果:
					DecimalFormat decimalFormat = new DecimalFormat();
					decimalFormat.setMaximumFractionDigits(2);// 设置保留两位有效数字

					if (busystart == null || idlestart == null) {
						// 添加到map中
						mapResult.put("sort", sort);
						mapResult.put("date", date);
						mapResult.put("busystart", busystart);
						mapResult.put("busyend", busyend);
						mapResult.put("idlestart", idlestart);
						mapResult.put("idleend", idleend);
						mapResult.put("busyspeed",
								decimalFormat.format(busyspeed));
						mapResult.put("idlespeed",
								decimalFormat.format(idlespeed));
						decimalFormat.setMaximumFractionDigits(4);// 设置保留四位有效数字
						mapResult.put("rate", decimalFormat.format(rate));

						mapResult.put("totals", totals);
						mapResult.put("floatss", floatss);
						mapResult.put("down_nums", down_nums);
						mapResult.put("down_speeds", down_speeds);

					} else {
						// 添加到map中
						mapResult.put("sort", sort);
						mapResult.put("date", date);
						mapResult.put("busystart",
								busystart.substring(0, busystart.length() - 3));
						mapResult.put("busyend", busyend);
						mapResult.put("idlestart",
								idlestart.substring(0, idlestart.length() - 3));
						mapResult.put("idleend", idleend);
						mapResult.put("busyspeed",
								decimalFormat.format(busyspeed));
						mapResult.put("idlespeed",
								decimalFormat.format(idlespeed));
						decimalFormat.setMaximumFractionDigits(4);// 设置保留四位有效数字
						mapResult.put("rate", decimalFormat.format(rate));

						mapResult.put("totals", totals);
						mapResult.put("floatss", floatss);
						mapResult.put("down_nums", down_nums);
						mapResult.put("down_speeds", down_speeds);

					}

					listResult.add(mapResult);
					// 将sort递增1：
					sort++;
				}// end of if listDate
			}// end of while it
		}// end of if map

		return listResult;

	}// end of handleData()

	public static void main(String[] args) {
		File file = new File("D:\\beijing\\20130805.sort");
		BufferedReader in = null;
		HashMap<String, HashMap<String, ArrayList<InputType>>> map = new HashMap();

		try {

			InputStreamReader reader = new InputStreamReader(
					new FileInputStream(file), "gbk");

			in = new BufferedReader(reader);
			String line;
			line = in.readLine();
			while ((line = in.readLine()) != null) {
				InputType inputType = new InputType(line);
				if (inputType.getDown_num() <=0){
					continue;
				}
				
				if (map.containsKey(inputType.getKeyin())) {
					map.get(inputType.getKeyin()).get(inputType.getKey())
							.add(inputType);

				} else {
					HashMap inmap = new HashMap<String, ArrayList<InputType>>();
					ArrayList<InputType> arr = new ArrayList<InputType>();
					arr.add(inputType);
					inmap.put(inputType.getKey(), arr);
					map.put(inputType.getKeyin(), inmap);
				}
			}

			Iterator itr = map.entrySet().iterator();
			int count = 1;
			while (itr.hasNext()) {
				Entry<String, HashMap<String, ArrayList<InputType>>> entry = (Entry<String, HashMap<String, ArrayList<InputType>>>) itr
						.next();
				// System.out.println(entry.getKey());
				HashMap<String, ArrayList<InputType>> value = entry.getValue();
				List<Map> list = handleData(value, "speed");
				if (list.size() > 1) {
					System.out.println("More Than One...");
				}
				Map mapd = list.get(0);
				System.out.println(count + "\t" + entry.getKey() + "\t"
						+ mapd.get("busystart") + "\t" + mapd.get("busyend")
						+ "\t" + mapd.get("idlestart") + "\t"
						+ mapd.get("idleend") + "\t" + mapd.get("busyspeed")
						+ "\t" + mapd.get("idlespeed") + "\t"
						+ mapd.get("totals") + "\t" + mapd.get("floatss")
						+ "\t" + mapd.get("down_nums") + "\t"
						+ mapd.get("down_speeds"));

				count++;
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(map.size());
		}
	}
}

class InputType {
	@Override
	public String toString() {
		return "InputType [date=" + date + ", bu=" + bu + ", time=" + time
				+ ", province=" + province + ", isp=" + isp + ", ispIn="
				+ ispIn + ", cityIn=" + cityIn + ", total=" + total
				+ ", floats=" + floats + ", down_num=" + down_num
				+ ", down_speed=" + down_speed + ", key=" + key + ", keyin="
				+ keyin + ", value=" + value + "]";
	}

	String date;
	String bu;
	String time;
	String province;
	String isp;
	String ispIn;
	String cityIn;
	Long total;
	Double floats;
	Long down_num;
	Double down_speed;

	String key;
	String keyin;
	String value;

	public String getKey() {
		return getDate();
	}

	public String getKeyin() {
		return getDate() + "\t" + getBu() + "\t" + getProvince() + "\t"
				+ getIsp() + "\t" + getIspIn() + "\t" + getCityIn();
	}

	public InputType(String input) {
		String[] inputs = input.split("\t");
		date = inputs[0];
		bu = inputs[1];
		time = inputs[2];
		province = inputs[3];
		isp = inputs[4];
		ispIn = inputs[5];
		cityIn = inputs[6];
		total = Long.parseLong(inputs[7]);
		floats = Double.parseDouble(inputs[8]);
		down_num = Long.parseLong(inputs[9]);
		if (down_num <=0){
			down_speed = 0d;
		}else{
			down_speed = Double.parseDouble(inputs[10]);			
		}
	}

	public String getBu() {
		return bu;
	}

	public Double getFloats() {
		return floats;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getProvince() {
		return province;
	}

	public String getIsp() {
		return isp;
	}

	public String getIspIn() {
		return ispIn;
	}

	public String getCityIn() {
		return cityIn;
	}

	public Double getDown_speed() {
		return down_speed * down_num;
	}

	public Long getTotal() {
		return total;
	}

	public Long getDown_num() {
		return down_num;
	}

}