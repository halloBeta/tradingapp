package com.example.trade.tradingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class TradingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingappApplication.class, args);
	}

}
