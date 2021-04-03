package com.example.callforservice.rest.mapper;

import com.example.callforservice.domain.Agency;
import com.example.callforservice.domain.Customer;
import com.example.callforservice.domain.Event;
import com.example.callforservice.domain.Responder;
import com.example.callforservice.rest.dto.EventDto;
import com.example.callforservice.rest.dto.ResponseEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventMapper {
  public Event toEvent(final EventDto eventDto) {
    Event event = new Event();
    event.setEventNumber(eventDto.getEventNumber());
    event.setEventTypeCode(eventDto.getEventTypeCode());
    event.setEventTime(eventDto.getEventTime());
    event.setDispatchTime(eventDto.getDispatchTime());

    Responder responder = new Responder();
    responder.setResponderCode(eventDto.getResponder());
    event.setResponder(responder);

    return event;
  }

  public ResponseEvent toResponseEvent(final Event event) {
    ResponseEvent responseEvent = new ResponseEvent();
    responseEvent.setAgencyId(
        Optional.ofNullable(event.getCustomer())
            .map(Customer::getAgency)
            .map(Agency::getAgencyId)
            .orElse(null));
    responseEvent.setEventId(event.getEventId());
    responseEvent.setEventNumber(event.getEventNumber());
    responseEvent.setEventTypeCode(event.getEventTypeCode());
    responseEvent.setEventTime(event.getEventTime());
    responseEvent.setDispatchTime(event.getDispatchTime());
    responseEvent.setResponder(event.getResponder().getResponderCode());

    return responseEvent;
  }
}
