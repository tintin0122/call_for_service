package com.example.callforservice.repository.mapper;

import com.example.callforservice.domain.Agency;
import com.example.callforservice.domain.Customer;
import com.example.callforservice.domain.Event;
import com.example.callforservice.domain.Responder;
import com.example.callforservice.repository.model.AgencyModel;
import com.example.callforservice.repository.model.CustomerModel;
import com.example.callforservice.repository.model.EventModel;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventModelMapper {
  public EventModel fromEvent(final Event event) {
    EventModel eventModel = new EventModel();
    eventModel.setEventId(UUID.randomUUID().toString());
    eventModel.setEventNumber(event.getEventNumber());
    eventModel.setEventTypeCode(event.getEventTypeCode());
    eventModel.setEventTime(event.getEventTime().atOffset(OffsetDateTime.now().getOffset()));
    eventModel.setDispatchTime(event.getDispatchTime().atOffset(OffsetDateTime.now().getOffset()));

    return eventModel;
  }

  public Event toEvent(final EventModel eventModel) {
    Event event = new Event();
    event.setEventId(eventModel.getEventId());
    event.setEventNumber(eventModel.getEventNumber());
    event.setEventTypeCode(eventModel.getEventTypeCode());
    event.setEventTime(eventModel.getEventTime().toLocalDateTime());
    event.setDispatchTime(eventModel.getDispatchTime().toLocalDateTime());

    Optional<CustomerModel> optionalCustomerModel =
        Optional.ofNullable(eventModel.getCustomerModel());
    Customer customer = new Customer();
    customer.setCustomerId(optionalCustomerModel.map(CustomerModel::getCustomerId).orElse(null));
    customer.setCustomerName(
        optionalCustomerModel.map(CustomerModel::getCustomerName).orElse(null));
    Agency agency = new Agency();
    agency.setAgencyId(
        optionalCustomerModel
            .map(CustomerModel::getCustomerAgency)
            .map(AgencyModel::getAgencyId)
            .orElse(null));
    agency.setAgencyName(
        optionalCustomerModel
            .map(CustomerModel::getCustomerAgency)
            .map(AgencyModel::getAgencyName)
            .orElse(null));
    customer.setAgency(agency);
    event.setCustomer(customer);

    Responder responder = new Responder();
    responder.setResponderCode(eventModel.getResponder().getResponderCode());
    responder.setResponderName(eventModel.getResponder().getResponderName());
    event.setResponder(responder);

    return event;
  }
}
