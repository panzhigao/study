package com.pan.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;


/**
 * @author panzhigao
 */
public class InitTheContext implements ApplicationListener<ContextRefreshedEvent> {
	

	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

	}
}
