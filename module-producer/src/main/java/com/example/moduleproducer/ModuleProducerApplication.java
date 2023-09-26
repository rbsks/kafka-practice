package com.example.moduleproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
@EntityScan(basePackages = {"com.example.modulecore"})
@EnableJpaRepositories(basePackages = {"com.example.modulecore"})
public class ModuleProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleProducerApplication.class, args);
    }

}
