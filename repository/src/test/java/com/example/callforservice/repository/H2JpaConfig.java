package com.example.callforservice.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.callforservice.repository")
@PropertySource(value = "classpath:repository-test.properties")
@EnableTransactionManagement
@EntityScan(basePackages = "com.example.callforservice.repository")
public class H2JpaConfig {
}
