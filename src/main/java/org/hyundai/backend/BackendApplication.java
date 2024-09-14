package org.hyundai.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BackendApplication {

	private static final Logger logger = LoggerFactory.getLogger(BackendApplication.class);

	public static void main(String[] args) {
		// Load environment variables from .env file
		Dotenv dotenv = Dotenv.configure().load();

		// Set System properties to use them in Spring Boot
		setSystemProperty("APP_NAME", dotenv.get("APP_NAME"));
		setSystemProperty("DB_HOST", dotenv.get("DB_HOST"));
		setSystemProperty("DB_PORT", dotenv.get("DB_PORT"));
		setSystemProperty("DB_NAME", dotenv.get("DB_NAME"));
		setSystemProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		setSystemProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		setSystemProperty("SERVER_PORT", dotenv.get("SERVER_PORT"));
		setSystemProperty("CORS_ALLOWED_ORIGINS", dotenv.get("CORS_ALLOWED_ORIGINS"));



		SpringApplication.run(BackendApplication.class, args);
	}

	private static void setSystemProperty(String key, String value) {
		if (value != null) {
			System.setProperty(key, value);
		} else {
			System.out.println("Warning: Environment variable '" + key + "' is not set.");
		}
	}

}
