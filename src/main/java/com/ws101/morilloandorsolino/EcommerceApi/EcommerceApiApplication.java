package com.ws101.morilloandorsolino.EcommerceApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the EcommerceApi Spring Boot application.
 *
 * <p>Starts the embedded Tomcat server and loads all Spring components.
 * Data is stored entirely in-memory using an {@code ArrayList<Product>}.
 *
 * @author Morillo and Orsolino
 */
@SpringBootApplication
public class EcommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}
}