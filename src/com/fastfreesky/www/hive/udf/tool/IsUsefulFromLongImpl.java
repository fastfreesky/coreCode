package com.fastfreesky.www.hive.udf.tool;

import java.io.IOException;

import com.fastfreesky.www.area.impl.FindInAreaFromLong;
import com.fastfreesky.www.area.interfaces.DialLineI;
import com.fastfreesky.www.tool.file.FileOperator;

/**
 * 
 * @ClassName: IsUsefulValue
 * @Description: TODO(这里用一句话描述这个类的作用)是否符合特定约束条件
 * @author tianyu.yang
 * @date 2013-8-12 下午4:55:25
 * 
 */
public class IsUsefulFromLongImpl implements DialLineI {

	protected FindInAreaFromLong<String> findValue = new FindInAreaFromLong<String>();

	/**
	 * 
	 * @Title: IsUsefulValue.java
	 * @Description:
	 * @param srcFile
	 *            来源文件
	 * @param isLocal
	 *            是否为本地文件还是Hadoop文件,且编码必须为utf-8编码格式
	 * @throws IOException
	 */
	public IsUsefulFromLongImpl(String srcFile, boolean isLocal)
			throws IOException {

		if (isLocal) {
			FileOperator.readFromLocalFile(srcFile, this);
		} else {
			FileOperator.readFromHadoopFile(srcFile, this);
		}

		findValue.readey();
	}

	@Override
	public boolean dialLine(String line) {
		// TODO Auto-generated method stub
		lineSplit = line.split("\t");
		if (lineSplit.length != 2 && lineSplit.length != 3) {
			return false;
		}

		if (lineSplit.length == 2) {
			findValue.addData(Long.parseLong(lineSplit[0]), lineSplit[1]);
		} else {
			findValue.addData(Long.parseLong(lineSplit[0]),
					Long.parseLong(lineSplit[1]), lineSplit[2]);
		}

		return true;
	}

	public String evaluate(String str, boolean isArea) {
		if (str == null || str.equalsIgnoreCase("null")) {
			return null;
		}
		// TODO Auto-generated method stub
		return findValue.get(Long.parseLong(str), isArea);
	}

	private String[] lineSplit;

}
