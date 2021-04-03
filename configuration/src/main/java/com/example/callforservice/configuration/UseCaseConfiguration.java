package com.example.callforservice.configuration;

import com.example.callforservice.usecase.CreateEventUseCase;
import com.example.callforservice.usecase.SearchEventUseCase;
import com.example.callforservice.usecase.port.EventRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {
  @Bean
  public CreateEventUseCase createEventUseCase(EventRepositoryPort eventRepositoryAdapter) {
    return new CreateEventUseCase(eventRepositoryAdapter);
  }

  @Bean
  public SearchEventUseCase createSearchEventUseCase(EventRepositoryPort eventRepositoryAdapter) {
    return new SearchEventUseCase(eventRepositoryAdapter);
  }
}
