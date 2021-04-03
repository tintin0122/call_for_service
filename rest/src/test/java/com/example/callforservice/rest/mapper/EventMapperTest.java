package com.example.callforservice.rest.mapper;

import com.example.callforservice.domain.Agency;
import com.example.callforservice.domain.Customer;
import com.example.callforservice.domain.Event;
import com.example.callforservice.domain.Responder;
import com.example.callforservice.rest.dto.EventDto;
import com.example.callforservice.rest.dto.ResponseEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EventMapperTest {
  @InjectMocks private EventMapper eventMapper;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testToEvent() {
    EventDto eventDto = new EventDto();
    Event event = eventMapper.toEvent(eventDto);
    Assertions.assertNotNull(event);
  }

  @Test
  public void testToResponseEvent() {
    Event event = new Event();
    Customer customer = new Customer();
    event.setCustomer(customer);
    Responder responder = new Responder();
    event.setResponder(responder);
    ResponseEvent responseEvent = eventMapper.toResponseEvent(event);
    Assertions.assertNotNull(responseEvent);
  }
}
