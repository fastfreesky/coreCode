package com.fastfreesky.www.hive.udf.tool;

import java.io.IOException;

import com.fastfreesky.www.impl.FindValueFromStringToTImpl;
import com.fastfreesky.www.impl.FindValueInAreaLongToTImpl;
import com.fastfreesky.www.interfaces.DialLineI;
import com.fastfreesky.www.tool.file.FileOperator;

/**
 * 
 * @ClassName: IsUsefulValueFromStringToStringImpl
 * @Description: TODO(这里用一句话描述这个类的作用)通过string获取string值
 * @author tianyu.yang
 * @date 2013-8-12 下午4:55:25
 * 
 */
public class IsUsefulValueFromStringToStringImpl implements DialLineI {

	protected FindValueFromStringToTImpl findValue = new FindValueFromStringToTImpl();

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
	public IsUsefulValueFromStringToStringImpl(String srcFile, boolean isLocal)
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
		if (lineSplit.length != 2) {
			return false;
		}
		findValue.addData(lineSplit[0], lineSplit[1]);
		return true;
	}

	public String evaluate(String str) {
		if (str == null || str.equalsIgnoreCase("null")) {
			return null;
		}
		// TODO Auto-generated method stub
		return findValue.get(str);
	}

	private String[] lineSplit;

}
