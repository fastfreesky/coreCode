package com.fastfreesky.www.impl;

import com.fastfreesky.www.generics.FindValueInArea;
import com.fastfreesky.www.tool.find.SearchAlgorithm;
import com.fastfreesky.www.tool.ip.Ip;

/**
 * 
 * @ClassName: LongGetString
 * @Description: TODO(这里用一句话描述这个类的作用)通过long型,匹配一个string类型数据
 * @author tianyu.yang
 * @param <T>
 * @date 2013-8-12 下午5:45:21
 * 
 */
public class FindValueInAreaLongToTImpl<T> extends FindValueInArea<Long, T> {

	public FindValueInAreaLongToTImpl() {
	}
	
	public FindValueInAreaLongToTImpl(int size) {
		super(size);
	}

	@Override
	protected void initKSize(int length) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		keyArrayStart = new Long[length];
		keyArrayEnd = new Long[length];
	}

	public T get(Long key) {
		// TODO Auto-generated method stub
		int stautus = SearchAlgorithm.findIpInArea(keyArrayStart, keyArrayEnd,
				key);
		if (stautus == -1) {
			return null;
		} else {
			return hashMapArea.get(keyArrayStart[stautus]);
		}
	}

}
