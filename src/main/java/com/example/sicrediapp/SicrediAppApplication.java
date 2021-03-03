package com.example.sicrediapp;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SicrediAppApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SicrediAppApplication.class, args);
	}

	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
				.info(new Info()
						.title("sicredi-app")
						.version("1.0")
						.contact(new Contact().email("fernando_cauper@hotmail.com"))
						.description("API voltada para Avaliação Técnica back-end")
				);
	}

}
