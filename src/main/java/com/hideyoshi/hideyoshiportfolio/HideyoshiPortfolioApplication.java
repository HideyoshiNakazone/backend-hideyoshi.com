package com.hideyoshi.hideyoshiportfolio;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class HideyoshiPortfolioApplication {

	@Value("${com.hideyoshi.frontEndPath}")
	private String frontEndPath;

	public static void main(String[] args) {
		SpringApplication.run(HideyoshiPortfolioApplication.class, args);
	}
}
