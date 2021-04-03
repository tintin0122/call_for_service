package com.example.callforservice.usecase.port;

import com.example.callforservice.domain.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryPort {
  Event create(final String userId, final Event event);

  List<Event> getEventsByResponder(final String responderCode, int page, int size, String[] sorts);

  List<Event> getEventsByTimeRange(
      final String userId,
      LocalDateTime startTime,
      LocalDateTime endTime,
      int page,
      int size,
      String[] sorts);
}
