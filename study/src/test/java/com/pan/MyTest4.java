package com.pan;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.junit.Test;

public class MyTest4 {
	
	@Test
	public void test1(){
		Set<String> set1=new HashSet<>();
		set1.add("1");
		set1.add("2");
		
		Set<String> set2=new HashSet<>();
		set2.add("1");
		set2.add("23");
		
		boolean deepEquals = Objects.deepEquals(set2, set1);
		System.out.println(deepEquals);
	}
	
}
