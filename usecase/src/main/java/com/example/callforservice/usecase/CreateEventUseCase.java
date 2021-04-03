package com.example.callforservice.usecase;

import com.example.callforservice.domain.Event;
import com.example.callforservice.usecase.port.EventRepositoryPort;

public class CreateEventUseCase {
  private EventRepositoryPort eventRepositoryPort;

  public CreateEventUseCase(EventRepositoryPort eventRepositoryPort) {
    this.eventRepositoryPort = eventRepositoryPort;
  }

  public Event create(final String userId, Event event) {
    return eventRepositoryPort.create(userId, event);
  }
}
