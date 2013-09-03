package com.fastfreesky.www.interfaces;

/**
 * 
 * @ClassName: AreaMethodI
 * @Description: TODO(这里用一句话描述这个类的作用)用于key_value类型及区间型的共用接口
 * @author tianyu.yang
 * @date 2013-9-2 上午10:37:32
 * 
 */
public interface AreaMethodI<K, T> {

	/**
	 * 
	 * @Title: addData
	 * @Description: TODO(这里用一句话描述这个方法的作用)增加数据接口
	 * @param key
	 *            唯一的key值
	 * @param value
	 *            对应唯一的value值 void
	 * @throws
	 */
	public void addData(K key, T value);

	/**
	 * 
	 * @Title: addData
	 * @Description: TODO(这里用一句话描述这个方法的作用)增加数据
	 * @param start
	 *            区间的起始端
	 * @param end
	 *            区间的结束端
	 * @param value
	 *            对应的Value值 void
	 * @throws
	 */
	public void addData(K start, K end, T value);

	/**
	 * 
	 * @Title: get
	 * @Description: TODO(这里用一句话描述这个方法的作用)通过key值,获取对应的vaue值
	 * @param key
	 * @return T
	 * @throws
	 */
	public abstract T get(K key);
	/**
	 * 
	 * @Title: readey 
	 * @Description: TODO(这里用一句话描述这个方法的作用)初始化操作,ready之后,可以进行get等操作
	 * void
	 * @throws
	 */
	public boolean readey();

}
