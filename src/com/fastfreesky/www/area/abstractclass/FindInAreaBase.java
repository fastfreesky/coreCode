package com.fastfreesky.www.area.abstractclass;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

import com.fastfreesky.www.area.interfaces.AreaMethodI;

/**
 * 
 * @ClassName: FindValueInArea
 * @Description: TODO(这里用一句话描述这个类的作用)查找某一值是否存在某一区间,若存在,取出相应的值
 * @author tianyu.yang
 * @date 2013-7-31 下午4:22:15
 * 
 * @param <K>区间数据,为Long,Integer等
 * @param <T>值类型 实现思路: 转化区间值的起始地址作为key值,只要符合区间,就通过区间的起始地址作为key,通过hashMap获取对应的值域
 */
public abstract class FindInAreaBase<K, T> implements AreaMethodI<K, T> {

	// 存放区间的首地址及对应的Value值
	protected HashMap<K, T> hashMapArea;
	protected K[] keyArrayStart;
	protected K[] keyArrayEnd;
	// 存放区间值
	protected HashMap<K, K> hashMapAreaKeyStartEnd;

	
	public FindInAreaBase() {
		hashMapArea = new HashMap<K, T>();
		hashMapAreaKeyStartEnd = new HashMap<K, K>();
	}

	@Override
	public void addData(K key) {
		// TODO Auto-generated method stub
		hashMapArea.put(key, null);
		hashMapAreaKeyStartEnd.put(key, null);
	}

	/**
	 * 
	 * @Title: addData
	 * @Description: TODO(这里用一句话描述这个方法的作用)新增数据区间
	 * @param start
	 *            起始
	 * @param end
	 *            终止
	 * @param value
	 *            对应的值 void
	 * @throws
	 */
	public void addData(K start, K end, T value) {
		hashMapArea.put(start, value);
		hashMapAreaKeyStartEnd.put(start, end);
	}

	/**
	 * 
	 * @Title: addData
	 * @Description: TODO(这里用一句话描述这个方法的作用)精确匹配,通过一个key精确匹配一个值
	 * @param key
	 * @param value
	 *            void
	 * @throws
	 */
	public void addData(K key, T value) {
		hashMapArea.put(key, value);
		hashMapAreaKeyStartEnd.put(key, null);
	}

	/**
	 * 
	 * @Title: initKSize
	 * @Description: TODO(这里用一句话描述这个方法的作用)初始化泛型K值的空间
	 * @param length
	 *            需要初始化的数组长度 void
	 * @throws
	 */
	protected abstract void initKSize(int length);

	/**
	 * 
	 * @Title: readey
	 * @Description: TODO(这里用一句话描述这个方法的作用)当数据新增完成后,调用该方法,实现初始化,之后就可以使用get方法 void
	 * @throws
	 */
	public boolean readey() {
		int length = hashMapAreaKeyStartEnd.size();

		initKSize(length);

		Iterator<K> itr = hashMapAreaKeyStartEnd.keySet().iterator();
		int i = 0;
		while (itr.hasNext()) {
			keyArrayStart[i++] = itr.next();
		}
		Arrays.sort(keyArrayStart);

		i = 0;
		for (Object a : keyArrayStart) {

			keyArrayEnd[i++] = hashMapAreaKeyStartEnd.get(a);
		}

		return true;
	}

}