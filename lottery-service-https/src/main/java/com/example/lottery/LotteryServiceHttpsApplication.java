package com.example.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// keytool -genkeypair -alias deepclouudlabs -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore dcl.p12 -validity 3650

@SpringBootApplication
public class LotteryServiceHttpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotteryServiceHttpsApplication.class, args);
	}

}
