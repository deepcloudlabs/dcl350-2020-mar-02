package com.example.hr.client.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;

public class BinanceWebSocketClient {
	private static final String URL = "wss://stream.binance.com:9443/ws/btcusdt@trade";

	public static void main(String[] args) throws InterruptedException {
		var client = new BinanceWebsocketClient();
		HttpClient.newHttpClient().newWebSocketBuilder().buildAsync(URI.create(URL), client);
		Thread.sleep(60_000);
	}

}

class BinanceWebsocketClient implements WebSocket.Listener {

	@Override
	public void onOpen(WebSocket webSocket) {
		System.out.println("Connected to binance!");
		webSocket.request(1);
	}

	@Override
	public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
		System.out.println(data);
		webSocket.request(1);
		return null;
	}

}
