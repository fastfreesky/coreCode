package com.fastfreesky.www.file.impl;

import java.util.ArrayList;

import com.fastfreesky.www.file.interfaces.FilterI;

public class FilterImpl implements FilterI {

	@Override
	public boolean exists(ArrayList<String> files) {
		// TODO Auto-generated method stub
		System.out.println(files);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
