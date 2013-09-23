package com.fastfreesky.www.area.interfaces;

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
	 * @Description: TODO(这里用一句话描述这个方法的作用)增加key值
	 * @param key
	 *            void
	 * @throws 该接口
	 *             ,增加只有一个数据,用于精确确定,某值是否在这些值之内
	 */
	public void addData(K key);

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

	/**
	 * 
	 * @Title: get
	 * @Description: TODO(这里用一句话描述这个方法的作用) 通过key值,获取对应的vaue值
	 * @param key
	 *            传入的Key值
	 * @param isArea
	 *            是否为区间匹配,true-是,false-精确匹配
	 * @return T
	 * @throws
	 */
	public T get(K key, boolean isArea);

	/**
	 * 
	 * @Title: isContain
	 * @Description: TODO(这里用一句话描述这个方法的作用) 判定key值是否存在数据列表中
	 * @param key
	 * @return boolean
	 * @throws
	 */
	public boolean isContain(K key);

	/**
	 * 
	 * @Title: readey
	 * @Description: TODO(这里用一句话描述这个方法的作用)初始化操作,ready之后,可以进行get等操作 void
	 * @throws
	 */
	public boolean readey();

}
