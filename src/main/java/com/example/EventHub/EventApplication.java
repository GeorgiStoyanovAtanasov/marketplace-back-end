package com.example.EventHub;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@EnableFeignClients
@OpenAPIDefinition(info = @Info(title = "JWT", description = "jwt"))
public class EventApplication {
    private static final Logger logger = LogManager.getLogger(EventApplication.class);
    public static void main(String[] args) {
        logger.error("5.This is an ERROR message.");
        SpringApplication.run(EventApplication.class, args);
    }
}
