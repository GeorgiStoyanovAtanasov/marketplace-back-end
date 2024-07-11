package com.example.EventHub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DBconfig {
    @Value("classpath:max-allowed-packet.sql")
    private org.springframework.core.io.Resource maxAllowedPacketScript;

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(maxAllowedPacketScript);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
