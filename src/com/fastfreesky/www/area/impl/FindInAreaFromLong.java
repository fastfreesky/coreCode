package com.fastfreesky.www.area.impl;

import com.fastfreesky.www.area.abstractclass.FindInAreaBase;
import com.fastfreesky.www.tool.algorithm.SearchAlgorithm;

/**
 * 
 * @ClassName: LongGetString
 * @Description: TODO(这里用一句话描述这个类的作用)通过long型,匹配一个string类型数据
 * @author tianyu.yang
 * @param <T>
 * @date 2013-8-12 下午5:45:21
 * 
 */
public class FindInAreaFromLong<T> extends FindInAreaBase<Long, T> {

	public FindInAreaFromLong() {
		super();
	}

	@Override
	protected void initKSize(int length) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		keyArrayStart = new Long[length];
		keyArrayEnd = new Long[length];
	}

	@Override
	public T get(Long key, boolean isArea) {
		// TODO Auto-generated method stub
		if (isArea) {
			int stautus = SearchAlgorithm.findLongInArea(keyArrayStart,
					keyArrayEnd, key);
			if (stautus == -1) {
				return null;
			} else {
				return hashMapArea.get(keyArrayStart[stautus]);
			}
		} else {
			return hashMapArea.get(key);
		}
	}

	@Override
	public boolean isContain(Long key) {
		// TODO Auto-generated method stub
		return hashMapArea.containsKey(key);
	}

}
