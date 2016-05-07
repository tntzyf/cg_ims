package com.cg.util;

import java.math.BigDecimal;

public class PriceTool {
	public static Float round(Float price){
		if(price == null){
			return null;
		}
		return new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	
	public static String roundToString(Float price){
		if(price == null){
			return null;
		}
		return new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
}
