package com.pan.test;

import java.lang.reflect.Field;
import org.junit.Test;
import com.pan.common.annotation.QueryParam;
import com.pan.test.base.BaseTest;
import com.pan.vo.QueryArticleVO;

/**
 * 
 * @author Administrator
 *
 */
public class ArticleTest extends BaseTest{

	
	@Test
	public void test1(){
		QueryArticleVO queryArticleVO=new QueryArticleVO();
		queryArticleVO.setType("1");
		queryArticleVO.setTitle("wo");
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void test2(){
		QueryArticleVO queryArticleVO=new QueryArticleVO();
		Class clazz=queryArticleVO.getClass();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			QueryParam annotation = field.getAnnotation(QueryParam.class);
			if(annotation!=null){
				boolean flag=annotation.highLightFlag();
				System.out.println(field.getName()+":"+flag);
			}
		}
	}
}
