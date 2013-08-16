package com.fastfreesky.www.hive.udf.tool;

import java.io.IOException;

import com.fastfreesky.www.impl.FindValueInAreaLongToTImpl;
import com.fastfreesky.www.interfaces.DialLineI;
import com.fastfreesky.www.tool.file.FileOperator;

/**
 * 
 * @ClassName: IsUsefulValue
 * @Description: TODO(这里用一句话描述这个类的作用)是否符合特定约束条件
 * @author tianyu.yang
 * @date 2013-8-12 下午4:55:25
 * 
 */
public abstract class IsUsefulValue implements DialLineI {

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
	public IsUsefulValue(String srcFile, boolean isLocal) throws IOException {

		if (isLocal) {
			FileOperator.readFromLocalFile(srcFile, this);
		} else {
			FileOperator.readFromHadoopFile(srcFile, this);
		}

		findValue.readey();
	}

	/**
	 * 
	 * @Title: evaluate
	 * @Description: TODO(这里用一句话描述这个方法的作用) 需要编写的代码段,处理输入,返回输出
	 * @param str
	 * @return String
	 * @throws
	 */
	public abstract String evaluate(String str);

	protected FindValueInAreaLongToTImpl<String> findValue = new FindValueInAreaLongToTImpl<String>(
			);

}
