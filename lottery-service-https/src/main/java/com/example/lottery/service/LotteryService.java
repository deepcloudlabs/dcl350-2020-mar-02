package com.example.lottery.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LotteryService {
	@Value("${lottery.min}")
	private int lotteryMin;
	@Value("${lottery.max}")
	private int lotteryMax;
	@Value("${lottery.size}")
	private int lotterySize;

	public List<List<Integer>> draw(int n) {
		return IntStream.range(0, n).mapToObj(i -> this.draw()).collect(Collectors.toList());
	}

	public List<Integer> draw() {
		return ThreadLocalRandom.current().ints(lotteryMin, lotteryMax).distinct().limit(lotterySize).sorted().boxed()
				.collect(Collectors.toList());
	}

}
