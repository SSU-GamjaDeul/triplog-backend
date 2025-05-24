package com.triplog;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(
		servers = {
				@Server(url = "https://triplog.store", description = "Production Server"),
				@Server(url = "http://localhost:8080", description = "Local Development Server")
		}
)
public class TriplogApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriplogApplication.class, args);
	}

}
