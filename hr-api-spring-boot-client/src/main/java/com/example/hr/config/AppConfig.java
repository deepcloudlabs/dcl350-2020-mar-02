package com.example.hr.config;

import java.util.ArrayList;
import java.util.List;

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
	public RestTemplate restTemplate() {
		return new RestTemplate();
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
