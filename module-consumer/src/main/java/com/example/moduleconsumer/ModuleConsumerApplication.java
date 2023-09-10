package com.example.moduleconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
@EntityScan(basePackages = {"com.example.modulecore"})
@EnableJpaRepositories(basePackages = {"com.example.modulecore"})
public class ModuleConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleConsumerApplication.class, args);
    }

}
