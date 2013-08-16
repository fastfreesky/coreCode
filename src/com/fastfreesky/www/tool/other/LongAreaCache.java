package com.fastfreesky.www.tool.other;

import java.util.Arrays;
import java.util.HashMap;

import com.fastfreesky.www.tool.find.SearchAlgorithm;

/**
 * 
 * @ClassName: LongAreaCache 
 * @Description: TODO(这里用一句话描述这个类的作用)尝试编写缓存策略,效果不理想,暂未使用 
 * @author  tianyu.yang
 * @date 2013-8-16 下午3:08:07 
 * 
 * @param <T>
 */
public class LongAreaCache<T> {
	private Long[] start;// 记录起始值
	private Long[] end;// 记录最终值
	private long[] status;// 记录区间值被调用的次数
	private HashMap<Long, T> value;
	private int index = 0;// 数组的索引
	private final int size;// 缓存大小
	private HashMap<Long, Long> hashMapAreaKeyStartEnd;
	private long cacheCount = 0;// 重新排序次数,每次有新数据表更,需要重新排序
	private long cacheCountHit = 0;// 命中次数
	private volatile boolean isOk = false;

	public LongAreaCache(int size) {
		start = new Long[size];
		end = new Long[size];
		status = new long[size];
		value = new HashMap<Long, T>(size);
		hashMapAreaKeyStartEnd = new HashMap<Long, Long>(size);
		index = 0;
		this.size = size;
	}

	public int getMin(long[] arr) {
		int min = 0;
		for (int x = 1; x < arr.length; x++) {
			if (arr[x] < arr[min])
				min = x;
		}
		return min;
	}

	public void add(Long start, Long end, T value) {

		// 尚未达到最低的排序值
		if (!isOk) {
			this.start[index] = start;
			this.end[index] = end;
			this.value.put(start, value);
			hashMapAreaKeyStartEnd.put(start, end);
			status[index] = 1;

			index++;

			if (index >= size) {
				isOk = true;
			}

		} else {
			// 判断哪个数据应该被踢出
			index = getMin(status);

			this.value.remove(this.start[index]);
			hashMapAreaKeyStartEnd.remove(this.start[index]);
			this.start[index] = start;
			this.end[index] = end;
			this.value.put(start, value);
			hashMapAreaKeyStartEnd.put(start, end);
			Sort();
		}

	}

	public T get(Long key) {
		if (!isOk) {
			return null;
		}
		int stautus = SearchAlgorithm.findIpInArea(start, end, key);
		if (stautus == -1) {
			return null;
		} else {
			cacheCountHit++;
			status[stautus]++;
			return value.get(start[stautus]);
		}
	}

	private void Sort() {
		Arrays.sort(start);

		int i = 0;
		for (Object a : start) {

			end[i++] = hashMapAreaKeyStartEnd.get(a);
		}

		cacheCount++;
	}

	public long getCacheCount() {
		return cacheCount;
	}

	public long getCacheCountHit() {
		return cacheCountHit;
	}

	public void printMaxCacheNum() {
		for (int i = 0; i < start.length; ++i) {
			System.out.println("Value in: [" + start[i] + "-" + end[i]
					+ "] num is : " + status[i]);
		}

	}
}
