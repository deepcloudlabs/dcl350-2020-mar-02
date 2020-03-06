package com.example.service.business;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.example.service.Calculator;

@Service
//@Quality(QualityLevel.SIMPLE)
//@Profile({ "dev", "test" })
@ConditionalOnProperty(name = "app.quality",havingValue = "simple")

public class SimpleCalculator implements Calculator {

	@Override
	public double add(double x, double y) {
		System.out.println("Simple::add");
		return x + y;
	}

	@Override
	public double sub(double x, double y) {
		return x - y;
	}

	@Override
	public double mul(double x, double y) {
		return x * y;
	}

	@Override
	public double div(double x, double y) {
		return x / y;
	}

}
