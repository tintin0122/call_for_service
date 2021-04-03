package com.example.callforservice.repository.mapper;

import com.example.callforservice.domain.Event;
import com.example.callforservice.repository.model.EventModel;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

public class EventModelMapperTest {
  @InjectMocks private EventModelMapper eventModelMapper;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testFromEvent() {
    Event event = new Event();
    event.setEventTime(LocalDateTime.now());
    event.setDispatchTime(LocalDateTime.now());
    EventModel eventModel = eventModelMapper.fromEvent(event);
    Assertions.assertTrue(Optional.ofNullable(eventModel).isPresent());
  }
}
