package com.pan.test;

import org.junit.Test;

import com.pan.test.base.BaseTest;
import com.pan.util.JedisUtils;

/**
 * 
 * @author Administrator
 *
 */
public class JedisTest extends BaseTest{
	
	@Test
	public void test1(){
		String str=JedisUtils.getString("1");
		System.out.println(str);
	}
	
	@Test
	public void test2(){
		JedisUtils.setString("p", "pzgd");
	}
	
	@Test
	public void test3(){
		JedisUtils.setStringExpire("p", "sssssss", 1);
	}
}
