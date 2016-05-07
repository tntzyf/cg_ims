package com.cg.source;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;

public class ResourceUtils {
	public static String readResource(String key){
		ResourceBundle resource = ResourceBundle.getBundle("com.cg.source.cgSource");
		return resource.getString(key);
	}
	
	public static void main(String args[]){
		System.out.println(readResource("jdSkuType"));
	}
}
