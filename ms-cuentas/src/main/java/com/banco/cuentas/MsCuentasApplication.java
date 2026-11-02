package com.banco.cuentas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsCuentasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsCuentasApplication.class, args);
	}

}
