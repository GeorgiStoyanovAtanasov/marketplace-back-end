package com.example.EventHub;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
@Testcontainers
public class DbSetupExtension implements BeforeAllCallback {

    @Container
    private static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.26")
        .withDatabaseName("marketplace-backend_test")
        .withUsername("root")
        .withPassword("");

    @Override
    public void beforeAll(ExtensionContext context) {
        if (!mysqlContainer.isRunning()) {
            mysqlContainer.start();
        }

        configureProperties();
    }

    private void configureProperties() {
        System.setProperty("spring.datasource.url", mysqlContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", mysqlContainer.getUsername());
        System.setProperty("spring.datasource.password", mysqlContainer.getPassword());
        System.setProperty("spring.datasource.hikari.maximum-pool-size", "2");
    }
}
