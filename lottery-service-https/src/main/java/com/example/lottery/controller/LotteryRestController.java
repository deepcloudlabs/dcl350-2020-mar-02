package com.example.lottery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lottery.service.LotteryService;

@RestController
@RequestMapping("numbers")
public class LotteryRestController {
	private LotteryService lotteryService;
	
	public LotteryRestController(LotteryService lotteryService) {
		this.lotteryService = lotteryService;
	}

	@GetMapping
	public List<List<Integer>> getNumbers(
			@RequestParam int n){
		return lotteryService.draw(n);
	}
}
