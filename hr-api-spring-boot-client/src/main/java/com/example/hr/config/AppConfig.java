package com.example.hr.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@Configuration
public class AppConfig {

	@Bean
	@LoadBalanced
	@Qualifier("loadBalanced")
	public RestTemplate loadBalancedRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	@Qualifier("standard")
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder.setConnectTimeout(Duration.ofSeconds(3)).setReadTimeout(Duration.ofSeconds(3))
				.build();
	}

	@Bean
	public WebSocketStompClient webSocketStompClient(WebSocketClient client) {
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(client));
		WebSocketClient transport = new SockJsClient(transports);
		final WebSocketStompClient webSocketStompClient = new WebSocketStompClient(transport);
		webSocketStompClient.setMessageConverter(new MappingJackson2MessageConverter());
		return webSocketStompClient;
	}

	@Bean
	public WebSocketClient webSocketClient() {
		return new StandardWebSocketClient();
	}
}
