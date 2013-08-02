package com.fastfreesky.www.tool.ip;

import com.fastfreesky.www.classtype.IpValue;
import com.fastfreesky.www.generics.FindValueInArea;
import com.fastfreesky.www.tool.find.SearchAlgorithm;
import com.fastfreesky.www.tool.time.TimeDiff;

public class ParseIp extends FindValueInArea<Long, IpValue<String>> {

	@Override
	protected void initKSize(int length) {
		// TODO Auto-generated method stub
		keyArrayStart = new Long[length];
		keyArrayEnd = new Long[length];
	}

	@Override
	public IpValue<String> get(Long key) {
		// TODO Auto-generated method stub
		int stautus = SearchAlgorithm.findIpInArea(keyArrayStart, keyArrayEnd,
				key);
		if (stautus == -1) {
			return null;
		} else {
			return hashMapIp.get(keyArrayStart[stautus]);
		}
	}

	public IpValue<String> getIp(Long key) {
		return get(key);
	}

	public IpValue<String> getIp(String ip) {
		int stautus = SearchAlgorithm.findIpInArea(keyArrayStart, keyArrayEnd,
				Ip.ipToLong(ip));
		if (stautus == -1) {
			return null;
		} else {
			return hashMapIp.get(keyArrayStart[stautus]);
		}
	}

	public static void main(String[] args) {
		ParseIp ip = new ParseIp();
		TimeDiff diff1 = new TimeDiff();
		diff1.Start();
		long st = 1;
		for (int i = 0; i < 300000; ++i) {
			IpValue<String> ipva = new IpValue<String>();
			ipva.setProvince("dddd");
			ip.addData(st++, st++, ipva);
		}
		diff1.End();
		diff1.printTimeDiff();

		// IpValue<String> ipvb = new IpValue<String>();
		// ipvb.setProvince("eeee");
		// ip.addData(1234l, 1236l, ipvb);
		//
		// IpValue<String> ipvc = new IpValue<String>();
		// ipvc.setProvince("ffff");
		// ip.addData(12345l, 123567l, ipvc);

		ip.readey();

		System.out.println(ip.getIp(0l));
		System.out.println(ip.getIp(200l).getProvince());
		System.out.println(ip.getIp(3000l).getProvince());
		System.out.println(ip.getIp(25000l).getProvince());
		System.out.println(ip.getIp(700000l));

		int i = 20000000;

		TimeDiff diff2 = new TimeDiff();
		diff2.Start();
		while (i > 0) {
			ip.getIp(0l);
			ip.getIp(200l).getProvince();
			ip.getIp(3000l).getProvince();
			ip.getIp(25000l).getProvince();
			ip.getIp(700000l);

			// System.out.println(ip.getIp(1100l));
			// System.out.println(ip.getIp(1234l).getProvince());
			// System.out.println(ip.getIp(1235l).getProvince());
			// System.out.println(ip.getIp(1236l).getProvince());
			// System.out.println(ip.getIp(1239l));
			//
			// System.out.println(ip.getIp(12342l));
			// System.out.println(ip.getIp(12345l).getProvince());
			// System.out.println(ip.getIp(123487l).getProvince());
			// System.out.println(ip.getIp(123567l).getProvince());
			// System.out.println(ip.getIp(1235670l));

			i--;
		}
		diff2.End();
		diff2.printTimeDiff();
	}

}
