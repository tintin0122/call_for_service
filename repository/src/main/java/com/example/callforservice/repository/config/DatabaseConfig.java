package com.example.callforservice.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@PropertySource(value = "classpath:repository.properties")
@EnableJpaRepositories("com.example.callforservice.repository")
@EntityScan("com.example.callforservice.repository")
public class DatabaseConfig {
}
