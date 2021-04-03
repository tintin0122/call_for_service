package com.example.callforservice.usecase;

import com.example.callforservice.domain.Event;
import com.example.callforservice.usecase.port.EventRepositoryPort;

import java.time.LocalDateTime;
import java.util.List;

public class SearchEventUseCase {

  private EventRepositoryPort eventRepositoryPort;

  public SearchEventUseCase(EventRepositoryPort eventRepositoryPort) {
    this.eventRepositoryPort = eventRepositoryPort;
  }

  public List<Event> searchEventsByEventTime(
      String userId,
      LocalDateTime startTime,
      LocalDateTime endTime,
      int page,
      int size,
      String[] sorts) {
    return eventRepositoryPort.getEventsByTimeRange(userId, startTime, endTime, page, size, sorts);
  }

  public List<Event> searchEventsByResponder(
      final String responderCode, int page, int size, String[] sorts) {
    return eventRepositoryPort.getEventsByResponder(responderCode, page, size, sorts);
  }
}
