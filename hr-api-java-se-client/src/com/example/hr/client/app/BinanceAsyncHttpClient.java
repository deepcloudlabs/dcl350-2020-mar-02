package com.example.hr.client.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BinanceAsyncHttpClient {
	private static final String URL = "https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";

	public static void main(String[] args) throws Exception {
		var counter = new AtomicInteger(0);
		var client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder().uri(URI.create(URL)).header("Accept", "application/json").build();
		var start = System.currentTimeMillis();
		for (var i = 0; i < 10; ++i) {
			client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
					.thenAccept(ticker -> {
						int sequence = counter.incrementAndGet();
						System.err.println(String.format("Response %d: %s", sequence, ticker));
						if (sequence == 10) {
							var stop = System.currentTimeMillis();
							System.out.println("Duration : " + (stop - start) + " ms.");
						}
					});
		}
		TimeUnit.SECONDS.sleep(10);
	}
}
