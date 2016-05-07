package com.cg.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import com.cg.domain.Item;
import com.cg.domain.Sku;

public class EntryComparator implements Comparator<Map.Entry<Sku,Integer>>{

	public int compare(Entry<Sku, Integer> o1, Entry<Sku, Integer> o2) {
		if(o1.getKey().getPrice()<o2.getKey().getPrice()){
			return 1;
		}else if(o1.getKey().getPrice()>o2.getKey().getPrice()){
			return -1;
		}
		return 0;
	}


}
