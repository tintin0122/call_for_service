package com.example.callforservice.rest.controller;

import com.example.callforservice.domain.Event;
import com.example.callforservice.rest.dto.EventDto;
import com.example.callforservice.rest.dto.ResponseEvent;
import com.example.callforservice.rest.mapper.EventMapper;
import com.example.callforservice.usecase.CreateEventUseCase;
import com.example.callforservice.usecase.SearchEventUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(summary = "Get events within time range")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
      })
  @GetMapping("/customers/{id}/events")
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

  @Operation(summary = "Create an Event")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Event created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ResponseEvent.class))
            }),
        @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
      })
  @PostMapping("/customers/{id}/events")
  public ResponseEntity<ResponseEvent> createEvent(
      @PathVariable(name = "id") String userId, @RequestBody EventDto eventDto) {
    Event event = createEventUseCase.create(userId, eventMapper.toEvent(eventDto));
    return new ResponseEntity<>(eventMapper.toResponseEvent(event), HttpStatus.OK);
  }

  @Operation(summary = "Get events which belong to responder")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = {@Content(mediaType = "application/json")}),
        @ApiResponse(responseCode = "403", description = "Unauthorized", content = @Content)
      })
  @GetMapping("/responders/{id}/events")
  public ResponseEntity<List<ResponseEvent>> getEventsByResponder(
      @PathVariable(name = "id") String responderId,
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
