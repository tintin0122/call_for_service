package com.example.callforservice.configuration;

import com.example.callforservice.repository.adapter.EventRepositoryAdapter;
import com.example.callforservice.usecase.port.EventRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfiguration {
    @Bean
    public EventRepositoryPort createEventRepositoryAdapter() {
        return new EventRepositoryAdapter();
    }
}
