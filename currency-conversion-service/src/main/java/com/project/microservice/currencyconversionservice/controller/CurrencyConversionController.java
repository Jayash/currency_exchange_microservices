package com.project.microservice.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.project.microservice.currencyconversionservice.CurrencyExchangeServiceProxy;
import com.project.microservice.currencyconversionservice.controller.bean.CurrencyConversionBean;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class CurrencyConversionController {
	
	private final CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quatity/{quantity}")
	public CurrencyConversionBean currencyConversionFeign(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		
		CurrencyConversionBean response = currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
		
		CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(response.getId(), 
				from, 
				to,
				response.getConversionMultiple(), 
				quantity, 
				quantity.multiply(response.getConversionMultiple()));
		
		currencyConversionBean.setPort(response.getPort());
		
		log.info("{}", response);
		
		return currencyConversionBean;
	}
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quatity/{quantity}")
	public CurrencyConversionBean currencyConversion(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class, 
				uriVariables);
		
		CurrencyConversionBean response = responseEntity.getBody();
		
		CurrencyConversionBean currencyConversionBean = new CurrencyConversionBean(response.getId(), 
				from, 
				to,
				response.getConversionMultiple(), 
				quantity, 
				quantity.multiply(response.getConversionMultiple()));

		
		return currencyConversionBean;
	}
}
