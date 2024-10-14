package com.gerenciamento_medico.medico_api;

import com.gerenciamento_medico.medico_api.config.DatabaseSeeder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = DatabaseSeeder.class))
public class MedicoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicoApiApplication.class, args);
	}

}
