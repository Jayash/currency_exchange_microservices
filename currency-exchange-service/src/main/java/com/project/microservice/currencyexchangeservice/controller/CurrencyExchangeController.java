package com.project.microservice.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.microservice.currencyexchangeservice.controller.bean.ExchangeValue;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class CurrencyExchangeController {
	
	private final Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		
		ExchangeValue exchangeValue = new ExchangeValue(1000L, from, to, BigDecimal.valueOf(73));
		
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		
		log.info("{}", exchangeValue);
		
		return exchangeValue;
	}
	
}
