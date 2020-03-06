package com.example.hr.service;

import java.lang.reflect.Type;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.example.hr.entity.Employee;

@Service
public class HrWebSocketClientService implements StompSessionHandler {
	@Autowired
	private WebSocketStompClient wssc;

	@PostConstruct
	public void connectToHrService() {
		wssc.connect("ws://localhost:5100/hr/api/v1/changes", this);
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return Employee.class;
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
		Employee emp = (Employee) payload;
		System.err.println("Recieved from WebSocket: " + emp);
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		System.err.println("Connected to HR MicroService instance!");
		session.subscribe("/topic/changes", this);
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		System.err.println(exception.getMessage());
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		System.err.println(exception.getMessage());
	}

}
