package com.example.callforservice.rest.controller;

import com.example.callforservice.domain.Event;
import com.example.callforservice.rest.dto.EventDto;
import com.example.callforservice.rest.dto.ResponseEvent;
import com.example.callforservice.rest.mapper.EventMapper;
import com.example.callforservice.usecase.CreateEventUseCase;
import com.example.callforservice.usecase.SearchEventUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class EventController {

  @Autowired private SearchEventUseCase searchEventUseCase;

  @Autowired private CreateEventUseCase createEventUseCase;

  @Autowired private EventMapper eventMapper;

  @GetMapping("/users/{id}/events")
  public ResponseEntity<List<ResponseEvent>> getEventsByTimeRange(
      @PathVariable(name = "id") String userId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") LocalDateTime startTime,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS") LocalDateTime endTime,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "eventTime,desc") String[] sort) {
    List<Event> events =
        searchEventUseCase.searchEventsByEventTime(userId, startTime, endTime, page, size, sort);
    return new ResponseEntity<>(
        Optional.ofNullable(events).orElse(new ArrayList<>()).stream()
            .map(event -> eventMapper.toResponseEvent(event))
            .collect(Collectors.toList()),
        HttpStatus.OK);
  }

  @PostMapping("/users/{id}/events")
  public ResponseEntity<ResponseEvent> createEvent(
      @PathVariable(name = "id") String userId, @RequestBody EventDto eventDto) {
    Event event = createEventUseCase.create(userId, eventMapper.toEvent(eventDto));
    return new ResponseEntity<>(eventMapper.toResponseEvent(event), HttpStatus.OK);
  }

  @GetMapping("/users/{id}/events")
  public ResponseEntity<List<ResponseEvent>> getEventsByResponder(
      @RequestParam(defaultValue = "", name = "responder") String responderId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size,
      @RequestParam(defaultValue = "eventTime,desc") String[] sort) {
    List<Event> events = searchEventUseCase.searchEventsByResponder(responderId, page, size, sort);
    return new ResponseEntity<>(
        Optional.ofNullable(events).orElse(new ArrayList<>()).stream()
            .map(event -> eventMapper.toResponseEvent(event))
            .collect(Collectors.toList()),
        HttpStatus.OK);
  }
}
