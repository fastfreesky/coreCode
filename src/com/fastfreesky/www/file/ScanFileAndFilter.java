package com.fastfreesky.www.file;

import com.fastfreesky.www.file.interfaces.FilterI;
import com.fastfreesky.www.file.interfaces.ScanFileI;

public class ScanFileAndFilter implements Runnable{
	
	private ScanFileI scanFile;
	private FilterI filterI;
	
	public ScanFileAndFilter(ScanFileI scanFile, FilterI filterI){
		this.scanFile = scanFile;
		this.filterI = filterI;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
