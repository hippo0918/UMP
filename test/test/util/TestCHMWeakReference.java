package test.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TestCHMWeakReference {

	public static void main(String[] args) {
		String key_aaa = "key_aaa";
		String key_bbb = "key_bbb";
		String key_ccc = "key_ccc";
		String key_ddd = "key_ddd";
		String value_aaa = "value_aaa";
		String value_bbb = "value_bbb";
		String value_ccc = "value_ccc";
		String value_ddd = "value_ddd";
		ConcurrentMap<String, String> chm = new ConcurrentHashMap<String, String>();
		Map<String, String> hm = new HashMap<String, String>();
		chm.put(key_aaa, value_aaa);
		chm.put(key_bbb, value_bbb);
		chm.put(key_ccc, value_ccc);
		chm.put(key_ddd, value_ddd);
		hm.put(key_aaa, value_aaa);
		hm.put(key_bbb, value_bbb);
		hm.put(key_ccc, value_ccc);
		hm.put(key_ddd, value_ddd);
		Iterator<Entry<String, String>> i = chm.entrySet().iterator();
		while(i.hasNext()) {
			Entry<String, String> entry = i.next();
			entry.setValue("aaa");
		}
		System.out.println(chm);
		System.out.println(hm);
		value_aaa = "aa";
		key_aaa = "aa";
		System.out.println(chm);
		System.out.println(hm);
	}
}
