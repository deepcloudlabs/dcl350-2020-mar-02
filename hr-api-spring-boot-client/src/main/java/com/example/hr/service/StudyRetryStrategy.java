package com.example.hr.service;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.hr.dto.Ticker;

@Service
public class StudyRetryStrategy {
	@Value("${binance.https.url}")
	private String binanceHttpsUrl;

	@Retryable(value = { SocketTimeoutException.class,
			SocketException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1_000))
	public Ticker getTicker() {
		RestTemplate rt = new RestTemplate();
		return rt.getForEntity(binanceHttpsUrl, Ticker.class).getBody();
	}
}
