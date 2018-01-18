package com.pan.test;

import org.junit.Test;

import com.pan.test.base.BaseTest;
import com.pan.util.JedisUtils;
import com.pan.util.RedisLock;




/**
 * 
 * @author Administrator
 *
 */
public class MyTest extends BaseTest{
	
	
	@Test
	public void test2() throws InterruptedException{
		for(int i=0;i<5;i++){
			Thread t=new Thread(new Runnable() {
				@Override
				public void run() {
					RedisLock lock=new RedisLock("world222",100);
					System.out.println(Thread.currentThread()+"開始");
					try {
//						 Long setnx = JedisUtils.setnx("ttttt", "ssss");
//						 if(setnx==1){	
//							 lock2=true;
//						 }
						if (lock.lock()){
							System.out.println(lock.isLocked()?Thread.currentThread().getName()+"获得锁成功":Thread.currentThread().getName()+"获得锁失败");

						}
					
						System.out.println(Thread.currentThread()+"結束");
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
						System.out.println(Thread.currentThread()+"finally進入:lock2:"+lock.isLocked());
						if(lock.isLocked()){	
//							try {
//								Thread.sleep(1000);
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
							System.out.println("finally");
							lock.unlock();
						}
					}
				}
			});
			t.start();
		}
	}
	
	@Test
	public void test3() throws InterruptedException{
		for(int i=0;i<5;i++){
			Thread t=new Thread(new Runnable() {
				@Override
				public void run() {
					Long setnx = JedisUtils.setnx("aaa", "bbbb");
					System.out.println(setnx);
				}
			});
			t.start();
		}
	}
	
	@Test
	public void test4() throws InterruptedException{
		for(int i=0;i<100;i++){
			//System.out.println(i);
			int num = Integer.parseInt(JedisUtils.getString("num"));
            num = num + 1;
            System.out.println("num:"+num);
            JedisUtils.setString("num",num+"");
            System.out.println(JedisUtils.getString("num"));
		}
//		for(int i=0;i<1;i++){
//			Thread t=new Thread(new Runnable() {
//				@Override
//				public void run() {
//					System.out.println("sss");
//					for(int i=0;i<100;i++){
//						System.out.println(i);
//						int num = Integer.parseInt(JedisUtils.getString("num"));
//			            num = num + 1;
//			            System.out.println("num:"+num);
//			            JedisUtils.setString("num",num+"");
//			            System.out.println(JedisUtils.getString("num"));
//					}
//				}
//			});
//			t.start();
//		}
	}
}
