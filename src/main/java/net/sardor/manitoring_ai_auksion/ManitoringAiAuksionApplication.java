package net.sardor.manitoring_ai_auksion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ManitoringAiAuksionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManitoringAiAuksionApplication.class, args);
	}

}
