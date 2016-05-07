package com.cg.util;

import java.util.Comparator;

import com.cg.domain.Item;

public class ItemComparator implements Comparator<Item>{

	public int compare(Item o1, Item o2) {
		if(o1.getTotalPrice()<o2.getTotalPrice()){
			return -1;
		}else if(o1.getTotalPrice()>o2.getTotalPrice()){
			return 1;
		}
		return 0;
	}
	
}
