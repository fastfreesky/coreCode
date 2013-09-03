package com.fastfreesky.www.impl;

import java.util.HashMap;

import com.fastfreesky.www.interfaces.AreaMethodI;

public class FindValueFromStringToTImpl implements AreaMethodI<String, String> {

	private HashMap<String, String> stringToString = new HashMap<String, String>();

	@Override
	public void addData(String key, String value) {
		// TODO Auto-generated method stub
		stringToString.put(key, value);
	}

	@Override
	public void addData(String start, String end, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return stringToString.get(key);
	}

	@Override
	public boolean readey() {
		// TODO Auto-generated method stub
		return false;
	}

}
