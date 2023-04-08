package com.isep.acme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ACMEApplication {

	public static void main(String[] args) {
		SpringApplication.run(ACMEApplication.class, args);
	}

}
