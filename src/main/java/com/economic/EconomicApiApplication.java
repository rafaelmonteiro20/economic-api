package com.economic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.economic.config.property.EconomicApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(EconomicApiProperty.class)
public class EconomicApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EconomicApiApplication.class, args);
	}
	
}
