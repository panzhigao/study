package com.pan.test;

import org.junit.Test;

import com.pan.test.base.BaseTest;
import com.pan.util.JedisUtils;

public class RedisTest extends BaseTest{
	
	@Test
	public void test1(){
		for(int i=0;i<100000;i++){
			JedisUtils.sadd("test",i+"");
		}
		System.out.println("成功");
		System.out.println(JedisUtils.scard("test"));
	}
	
	@Test
	public void test2(){
		System.out.println(JedisUtils.scard("test"));
		//for(int i=1000001;i<100200;i++){
			JedisUtils.sadd("test","a");
		//}
		System.out.println(JedisUtils.scard("test"));
	}
}
