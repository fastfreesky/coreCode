package com.fastfreesky.www.interfaces;

import org.apache.hadoop.io.Text;

import com.fastfreesky.www.impl.FindValueInAreaLongToTImpl;

public interface DialLineI {
	/**
	 * 
	 * @Title: dialLine 
	 * @Description: TODO(这里用一句话描述这个方法的作用)处理行记录 
	 * @param line
	 * @return    
	 * boolean
	 * @throws
	 */
	boolean dialLine(String line);
}
