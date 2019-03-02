package com.pan.test.base;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Administrator
 * 测试基类
 * 使用junit4进行测试 
 */
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:spring/spring.xml"}) 
@Transactional(value="transactionManager")
public class BaseTest {
	
	@Test
	public void test1(){
		
	}
	
}
