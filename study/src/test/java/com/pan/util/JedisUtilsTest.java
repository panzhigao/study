package com.pan.util;

import com.pan.test.base.BaseTest;
import org.junit.Test;

import java.util.List;

public class JedisUtilsTest extends BaseTest{

    @Test
    public void scan() throws Exception {
        String key="a:*";
        List<String> scan = JedisUtils.scan(key, 100);
        scan.forEach(s-> System.out.println(s));
    }

    @Test
    public void scan1() throws Exception {
        String key="a:*";
        List<byte[]> scan = JedisUtils.scan(key.getBytes(), 100);
        for (byte[] aa:scan){
            System.out.println(new String(aa));
        }
    }
}