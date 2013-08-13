package com.fastfreesky.www.hive.udf.tool;

/**
 * 
 * @ClassName: IsUsefulValueImpl
 * @Description: TODO(这里用一句话描述这个类的作用)一种Long型区间匹配最终字符串的匹配实现算法
 * @author tianyu.yang
 * @date 2013-8-12 下午6:31:12
 * 
 */
public class IsUsefulValueImpl extends IsUsefulValue {

	public IsUsefulValueImpl(String srcFile) {
		super(srcFile);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean dialLine(String line) {
		// TODO Auto-generated method stub
		lineSplit = line.split("\t");
		if (lineSplit.length != 2) {
			return false;
		}

		findValue.addData(Long.parseLong(lineSplit[0]), lineSplit[1]);

		return true;
	}

	@Override
	public String evaluate(String str) {
		if (str == null || str.equalsIgnoreCase("null")) {
			return null;
		}
		// TODO Auto-generated method stub
		return findValue.get(Long.parseLong(str));
	}

	private String[] lineSplit;
}
