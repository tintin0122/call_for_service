package com.example.callforservice.repository.adapter;

import com.example.callforservice.domain.Event;
import com.example.callforservice.repository.mapper.EventModelMapper;
import com.example.callforservice.repository.model.EventModel;
import com.example.callforservice.repository.service.EventService;
import com.example.callforservice.usecase.port.EventRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventRepositoryAdapter implements EventRepositoryPort {

  @Autowired private EventService eventService;

  @Autowired private EventModelMapper eventModelMapper;

  @Override
  public Event create(String userId, Event event) {
    return eventModelMapper.toEvent(eventService.createEvent(userId, event));
  }

  @Override
  public List<Event> getEventsByResponder(
      String responderCode, int page, int size, String[] sorts) {
    List<EventModel> eventModels =
        eventService.getEventsByResponder(responderCode, page, size, sorts);
    return Optional.ofNullable(eventModels).orElse(Collections.emptyList()).stream()
        .map(model -> eventModelMapper.toEvent(model))
        .collect(Collectors.toList());
  }

  @Override
  public List<Event> getEventsByTimeRange(
      String userId,
      LocalDateTime startTime,
      LocalDateTime endTime,
      int page,
      int size,
      String[] sorts) {
    List<EventModel> eventModels =
        eventService.getEventByTimeRange(userId, startTime, endTime, page, size, sorts);
    return Optional.ofNullable(eventModels).orElse(Collections.emptyList()).stream()
        .map(model -> eventModelMapper.toEvent(model))
        .collect(Collectors.toList());
  }
}
