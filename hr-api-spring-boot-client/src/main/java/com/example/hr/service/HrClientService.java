package com.example.hr.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hr.entity.Employee;

@Service
public class HrClientService {
	@Autowired
	private HrService hrSrv;
	@Autowired
	private StudyRetryStrategy myBinanceSrv;

	// @Scheduled(fixedRate = 1_000)
	public void callHrApi() {
		System.err.println(hrSrv.getClass());
		String msg = hrSrv.getir(0, 10).stream().map(Employee::getFullname).collect(Collectors.joining(",", "[", "]"));
		System.out.println(msg);
	}

	// @Scheduled(fixedRate = 10_000)
	public void callBinanceApi() {
		System.out.println(myBinanceSrv.getTicker());
	}
}
