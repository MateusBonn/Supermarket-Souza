package com.supermarketSouza.SupermarketSouza;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SupermarketSouzaApplication {

	public static void main(String[] args) {

		SpringApplication.run(SupermarketSouzaApplication.class, args);
		System.out.print("Hora de validação do Bearer");
		System.out.println(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")));
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/login").allowedOrigins("http://localhost:3000");

			}
		};
	}

}
