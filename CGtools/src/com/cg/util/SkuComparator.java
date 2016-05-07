package com.cg.util;

import java.util.Comparator;

import com.cg.domain.Item;
import com.cg.domain.Sku;

public class SkuComparator implements Comparator<Sku>{

	public int compare(Sku o1, Sku o2) {
		if(o1.getPrice()>o2.getPrice()){
			return -1;
		}else if(o1.getPrice()<o2.getPrice()){
			return 1;
		}
		return 0;
	}

	
}
