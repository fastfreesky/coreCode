package com.fastfreesky.www.file.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.fastfreesky.www.file.interfaces.FilterI;
import com.fastfreesky.www.file.interfaces.ScanFileI;

public class ScanFileImpl implements ScanFileI, Runnable {

	public ScanFileImpl() {

	}

	public ScanFileImpl(FilterI filter) {
		this.filter = filter;
	}

	public void setLoopTime(long loopTime) {
		this.loopTime = loopTime;
	}

	@Override
	public void addScanPath(String scanPath) {
		// TODO Auto-generated method stub
		if (!filePath.contains(scanPath)) {
			filePath.add(scanPath);
			log.debug("AddScanPath: " + scanPath);
		} else {
			log.debug("Already Exists: " + scanPath);
		}
	}

	@Override
	public void addScanPath(ArrayList<String> scanPath) {
		// TODO Auto-generated method stub
		for (String path : scanPath) {
			addScanPath(path);
		}
	}

	@Override
	public void removeScanPath(String scanPath) {
		// TODO Auto-generated method stub
		if (filePath.contains(scanPath)) {
			filePath.remove(scanPath);
			log.debug("RemoveScanPath: " + scanPath);
		} else {
			log.debug("Not Exists: " + scanPath);
		}
	}

	@Override
	public void removeScanPath(ArrayList<String> scanPath) {
		// TODO Auto-generated method stub
		for (String path : scanPath) {
			removeScanPath(path);
		}
	}

	@Override
	public void removeScanPathAll() {
		// TODO Auto-generated method stub
		filePath.clear();
	}

	@Override
	public void overScanPath(String scanPath) {
		// TODO Auto-generated method stub
		removeScanPathAll();
		addScanPath(scanPath);
	}

	@Override
	public void overScanPath(ArrayList<String> scanPath) {
		// TODO Auto-generated method stub
		removeScanPathAll();
		addScanPath(scanPath);
	}

	@Override
	public ArrayList<String> getScanFile() {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<String>> filePathS = getScanFileAll();
		if (filePathS == null) {
			return null;
		}
		Iterator<Entry<String, ArrayList<String>>> itr = filePathS.entrySet()
				.iterator();

		ArrayList<String> out = new ArrayList<String>();
		while (itr.hasNext()) {
			Entry<String, ArrayList<String>> ent = itr.next();
			out.addAll(ent.getValue());
		}

		return (out.size() > 0) ? out : null;
	}

	@Override
	public ArrayList<String> getScanFile(String path) {
		// TODO Auto-generated method stub
		ArrayList<String> array = new ArrayList<String>();
		getScanFile(path, array);

		return (array.size() > 0) ? array : null;
	}

	@Override
	public void getScanFile(String path, ArrayList<String> out) {
		// TODO Auto-generated method stub
		File f = new File(path);
		if (f.exists()) {
			if (f.isDirectory()) {
				File file[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					// 创建新的集合
					File newfiletype = file[j];
					if (newfiletype.isDirectory()) {
						getScanFile(newfiletype.getAbsolutePath(), out);
					} else {
						out.add(newfiletype.getAbsolutePath());
					}
				}

			} else {
				out.add(f.getAbsolutePath());
			}
		}
	}

	@Override
	public HashMap<String, ArrayList<String>> getScanFileAll() {
		// TODO Auto-generated method stub
		HashMap<String, ArrayList<String>> hashFilePath = new HashMap<String, ArrayList<String>>();

		ArrayList<String> fileS = getFilePath();
		if (fileS == null) {
			return null;
		}

		for (String fileP : fileS) {
			ArrayList<String> array = new ArrayList<String>();
			getScanFile(fileP, array);
			hashFilePath.put(fileP, array);
		}

		return (hashFilePath.size() > 0) ? hashFilePath : null;
	}

	public ArrayList<String> getFilePath() {
		return (filePath.size() > 0) ? filePath : null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		timer = new Timer();
		timer.schedule(new TimerTask() {
			int count = 1;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (!isScan) {
					isScan = true;
					log.info("Scan times: " + count++);
					getScanFileReal();
					if (filter != null) {
						ArrayList<String> vl = getAddFiles();
						if (vl.size() > 0) {
							filter.exists(vl);
						}
					}

					isScan = false;
				}

			}
		}, 0, loopTime);

		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (timer != null) {
			timer.cancel();
		}
		log.info("Scan Stop");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		new Thread(this).start();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		synchronized (this) {
			notifyAll();
		}
	}

	private synchronized void getScanFileReal() {
		// 保存之前记录值
		prevFiles.clear();
		prevFiles.addAll(currentFiles);
		currentFiles.clear();

		currentFiles = getScanFile();
		for (String cu : currentFiles) {
			if (!prevFiles.contains(cu)) {
				synchronized (addFiles) {
					addFiles.add(cu);
				}
			}
		}

	}

	public ArrayList<String> getAddFiles() {
		synchronized (addFiles) {
			ArrayList<String> out = new ArrayList<String>(addFiles);
			addFiles.clear();
			return out;
		}
	}

	/**
	 * 存储要分析的路径
	 */
	private ArrayList<String> filePath = new ArrayList<String>();
	private static Logger log = Logger.getLogger(ScanFileImpl.class);
	private Timer timer = null;
	private long loopTime = 30000;
	private volatile boolean isScan = false;// 是否可以进行再次Scan,防止上次任务时间过长而尚未完成扫描

	private ArrayList<String> prevFiles = new ArrayList<String>();// 存储原来文件路径
	private ArrayList<String> currentFiles = new ArrayList<String>();// 存储当前文件路径
	private ArrayList<String> addFiles = new ArrayList<String>();// 存储新增的文件

	private FilterI filter;

	public static void main(String[] args) {
		ScanFileImpl scan = new ScanFileImpl(new FilterImpl());
		String path1 = "D:\\test";
		// String path2 = "D:\\Application";

		scan.addScanPath(path1);
		// scan.addScanPath(path2);

		// System.out.println(scan.getFilePath());
		// System.out.println(scan.getScanFile());
		//
		// scan.removeScanPath(path2);
		// System.out.println(scan.getFilePath());
		// System.out.println(scan.getScanFile());
		//
		// scan.removeScanPathAll();
		// System.out.println(scan.getFilePath());
		// System.out.println(scan.getScanFile());
		scan.onStart();

		int count = 1;
		while (true) {
			try {
				Thread.sleep(6000);
				// int size = scan.getAddFiles().size();
				// System.out.println(count + "\t" + size);
				count++;

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
