package com.project.microservices.limitsservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.microservices.limitsservice.bean.LimitConfiguration;
import com.project.microservices.limitsservice.config.Configuration;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class LimitsConfigurationController {

	private Configuration configuration;

	@GetMapping("/limits")
	public LimitConfiguration retrieveLimitsFromConfigurations() {
		LimitConfiguration limitConfiguration = new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
		return limitConfiguration;
	}

}
