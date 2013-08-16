package com.fastfreesky.www.main;

import java.io.IOException;

import com.fastfreesky.www.hive.udf.tool.IsUsefulValueImpl;

public class Main {

	public static void main(String[] args) {
		for (int i = 0; i < 2; ++i) {
			IsUsefulValueImpl ma = null;
			try {
				ma = new IsUsefulValueImpl("D:\\a.txt", false);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(ma.evaluate("1"));
			ma = null;
			System.out.println("Exit11" + ma);
			System.gc();
		}
	}

}
