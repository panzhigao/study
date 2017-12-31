package com.pan.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Administrator
 *
 */
public class MyTest {
	public static void main(String[] args) {
		Map<String,Object> map=new HashMap<String, Object>(2);
		map.put("aa", "bb");
		String userId=(String) map.get("userId");
		if(userId==null){
			System.out.println("ssss");
		}
	}
}
