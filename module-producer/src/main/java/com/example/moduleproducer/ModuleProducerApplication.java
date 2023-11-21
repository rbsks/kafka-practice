package com.example.moduleproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class ModuleProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleProducerApplication.class, args);
    }

}
