package com.pan.util;

import com.pan.common.constant.MyConstant;
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
    
    @Test
    public void blpop() throws Exception {
        List<String> blpop = JedisUtils.blpop(MyConstant.ARTICLE_ES_REDIS_LIST);
        System.out.println(blpop);
    }

    @Test
    public void getString() throws Exception {
        String global_system_id = JedisUtils.getString("global_system_id");
        System.out.println(global_system_id);
    }
    
}