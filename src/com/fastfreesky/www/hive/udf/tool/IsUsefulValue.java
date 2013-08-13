package com.fastfreesky.www.hive.udf.tool;

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

	public IsUsefulValue(String srcFile) {
		FileOperator.readFromFile(srcFile, this);

		findValue.readey();
	}

	public abstract String evaluate(String str);

	protected FindValueInAreaLongToTImpl<String> findValue = new FindValueInAreaLongToTImpl<String>();

}
