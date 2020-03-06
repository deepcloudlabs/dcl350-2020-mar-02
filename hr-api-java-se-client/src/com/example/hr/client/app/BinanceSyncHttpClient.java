package com.example.hr.client.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BinanceSyncHttpClient {
	private static final String URL = "https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";

	public static void main(String[] args) throws Exception {
		var client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder().uri(URI.create(URL)).header("Accept", "application/json").build();
		var start = System.currentTimeMillis();
		var ticker = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
		System.out.println(ticker);
		var stop = System.currentTimeMillis();
		System.out.println("Duration : " + (stop - start) + " ms.");
	}

}
