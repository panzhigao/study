package com.pan.websocket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * 
 * @author Administrator
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	
	@Value("${domain}")
	private String domain;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		 registry.addHandler(myHandler(), "/myHandler").addInterceptors(new WebSocketInterceptor())
		 .setAllowedOrigins(domain);
	}
	
    @Bean
    public WebSocketHandler myHandler() {
        return new MyWebSocketHandler();
    }
}
