package com.uriel.spring.security.postgres.SpringBootSecurityApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class SpringBootSecurityApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));
        System.setProperty("SPRING_DATASOURCE_USERNAME", dotenv.get("SPRING_DATASOURCE_USERNAME"));
        System.setProperty("SPRING_DATASOURCE_PASSWORD", dotenv.get("SPRING_DATASOURCE_PASSWORD"));
        System.setProperty("BEZKODER_APP_JWTSECRET", dotenv.get("BEZKODER_APP_JWTSECRET"));
        System.setProperty("BEZKODER_APP_JWTEXPIRATIONMS", dotenv.get("BEZKODER_APP_JWTEXPIRATIONMS"));

					SpringApplication.run(SpringBootSecurityApplication.class, args);
	}

}
