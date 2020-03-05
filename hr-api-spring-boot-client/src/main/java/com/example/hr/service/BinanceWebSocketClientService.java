package com.example.hr.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

//@Service
public class BinanceWebSocketClientService implements WebSocketHandler {
	@Autowired
	private WebSocketClient wsc;

	@PostConstruct
	public void connectToBinance() {
		wsc.doHandshake(this, "wss://stream.binance.com:9443/ws/btcusdt@trade");
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.err.println("Connected to binance.");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		String payload = message.getPayload().toString();
		System.err.println(payload);
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}
}
