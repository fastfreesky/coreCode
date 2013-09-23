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
public class FindInAreaFromString<T> extends FindInAreaBase<String, T> {

	public FindInAreaFromString() {
		super();
	}

	@Override
	protected void initKSize(int length) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		keyArrayStart = new String[length];
		keyArrayEnd = new String[length];
	}

	@Override
	public T get(String key, boolean isArea) {
		// TODO Auto-generated method stub
		return hashMapArea.get(key);
	}

	@Override
	public boolean isContain(String key) {
		// TODO Auto-generated method stub
		return hashMapArea.containsKey(key);
	}

	public static void main(String[] args) {
		FindInAreaFromString<String> findInAreaFromString = new FindInAreaFromString<String>();
		findInAreaFromString.addData("abc", "ccc");
		findInAreaFromString.addData("def", "ccc");
		findInAreaFromString.addData("hij", "ccc");

		findInAreaFromString.readey();

		System.out.println(findInAreaFromString.get(null, false));
		System.out.println(findInAreaFromString.get("null", false));
		System.out.println(findInAreaFromString.get("abc", false));
		System.out.println(findInAreaFromString.get("def", false));
		System.out.println(findInAreaFromString.get("hij", false));
		System.out.println(findInAreaFromString.get("dddd", false));
		
		System.out.println(findInAreaFromString.isContain(null));
		System.out.println(findInAreaFromString.isContain("null"));
		System.out.println(findInAreaFromString.isContain("abc"));
		System.out.println(findInAreaFromString.isContain("def"));
		System.out.println(findInAreaFromString.isContain("hij"));
		System.out.println(findInAreaFromString.isContain("dddd"));
	}
}
