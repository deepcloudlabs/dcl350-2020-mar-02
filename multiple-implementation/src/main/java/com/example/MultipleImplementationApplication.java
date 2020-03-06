package com.example;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.service.Calculator;

@SpringBootApplication
public class MultipleImplementationApplication implements ApplicationRunner {
	@Autowired
	private List<Calculator> calculators;
	
	@Autowired
	private Map<String,Calculator> calculators2;
	
	@Autowired
	// @Quality(QualityLevel.COMPLEX)
	private Calculator calc;

	public static void main(String[] args) {
		SpringApplication.run(MultipleImplementationApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(calc.add(3, 5));
	}

}
