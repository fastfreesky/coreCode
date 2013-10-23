package com.fastfreesky.www.file.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

public interface ScanFileI {

	/**
	 * 
	 * @Title: addScanPath
	 * @Description: TODO(这里用一句话描述这个方法的作用)追加单个扫描路径
	 * @param scanPath
	 *            void
	 * @throws
	 */
	public void addScanPath(String scanPath);

	/**
	 * 
	 * @Title: addScanPath
	 * @Description: TODO(这里用一句话描述这个方法的作用)追加扫描路径集合
	 * @param scanPath
	 *            void
	 * @throws
	 */
	public void addScanPath(ArrayList<String> scanPath);

	/**
	 * 
	 * @Title: removeScanPath
	 * @Description: TODO(这里用一句话描述这个方法的作用)移除某个扫描路径
	 * @param scanPath
	 *            void
	 * @throws
	 */
	public void removeScanPath(String scanPath);

	/**
	 * 
	 * @Title: removeScanPath
	 * @Description: TODO(这里用一句话描述这个方法的作用)移除一个集合的扫描路径
	 * @param scanPath
	 *            void
	 * @throws
	 */
	public void removeScanPath(ArrayList<String> scanPath);

	/**
	 * 
	 * @Title: removeScanPathAll
	 * @Description: TODO(这里用一句话描述这个方法的作用)全部移除 void
	 * @throws
	 */
	public void removeScanPathAll();

	/**
	 * 
	 * @Title: overScanPath
	 * @Description: TODO(这里用一句话描述这个方法的作用)覆盖单个扫描路径
	 * @param scanPath
	 *            void
	 * @throws
	 */
	public void overScanPath(String scanPath);

	/**
	 * 
	 * @Title: overScanPath
	 * @Description: TODO(这里用一句话描述这个方法的作用)用一个新的集合覆盖现有扫描路径
	 * @param scanPath
	 *            void
	 * @throws
	 */
	public void overScanPath(ArrayList<String> scanPath);

	/**
	 * 
	 * @Title: getScanFile
	 * @Description: TODO(这里用一句话描述这个方法的作用)获取全部路径下的文件名集合
	 * @return ArrayList<String>
	 * @throws
	 */
	public ArrayList<String> getScanFile();

	/**
	 * 
	 * @Title: getScanFile
	 * @Description: TODO(这里用一句话描述这个方法的作用)获取特定路径下的文件名
	 * @param path
	 * @return ArrayList<String>
	 * @throws
	 */
	public ArrayList<String> getScanFile(String path);

	/**
	 * 
	 * @Title: getScanFile
	 * @Description: TODO(这里用一句话描述这个方法的作用)获取特定路径下返回的文件名集合
	 * @param path
	 * @param out
	 *            --输出的路径
	 * @return ArrayList<String>
	 * @throws
	 */
	public void getScanFile(String path, ArrayList<String> out);

	/**
	 * 
	 * @Title: getScanFileAll
	 * @Description: TODO(这里用一句话描述这个方法的作用)获取每个路径下对应的文件名Hash集合
	 * @return HashMap<String,ArrayList<String>>
	 * @throws
	 */
	public HashMap<String, ArrayList<String>> getScanFileAll();

	/**
	 * 
	 * @Title: onStart 启动扫描,主要服务于定点获取文件列表
	 * @Description: TODO(这里用一句话描述这个方法的作用) void
	 * @throws
	 */
	public void onStart();

	/**
	 * 
	 * @Title: stop
	 * @Description: TODO(这里用一句话描述这个方法的作用)停止扫描任务 void
	 * @throws
	 */
	public void stop();
}
