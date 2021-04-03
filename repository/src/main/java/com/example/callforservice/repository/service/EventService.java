package com.example.callforservice.repository.service;

import com.example.callforservice.domain.Event;
import com.example.callforservice.domain.Responder;
import com.example.callforservice.repository.EventRepository;
import com.example.callforservice.repository.ResponderRepository;
import com.example.callforservice.repository.CustomerRepository;
import com.example.callforservice.repository.mapper.EventModelMapper;
import com.example.callforservice.repository.model.AgencyModel;
import com.example.callforservice.repository.model.EventModel;
import com.example.callforservice.repository.model.ResponderModel;
import com.example.callforservice.repository.model.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

  private static final String ASC = "asc";
  private static final String DESC = "desc";
  public static final String COMMA = ",";

  @Autowired private EventRepository eventRepository;

  @Autowired private CustomerRepository customerRepository;

  @Autowired private ResponderRepository responderRepository;

  @Autowired private EventModelMapper eventModelMapper;

  @Transactional
  public EventModel createEvent(final String userId, final Event event) {
    Optional<CustomerModel> userModelOptional = customerRepository.findById(Long.valueOf(userId));
    EventModel eventModel = eventModelMapper.fromEvent(event);
    eventModel.setCustomerModel(userModelOptional.orElse(null));
    ResponderModel responderModel =
        responderRepository.findByResponderCode(
            Optional.ofNullable(event.getResponder()).map(Responder::getResponderCode).orElse(""));
    eventModel.setResponder(responderModel);

    return eventRepository.save(eventModel);
  }

  public List<EventModel> getEventsByResponder(
      String responderCode, int page, int size, String[] sorts) {
    Pageable pagingSort = constructPageable(page, size, sorts);

    return eventRepository.findByResponder(responderCode, pagingSort).getContent();
  }

  private Pageable constructPageable(int page, int size, String[] sorts) {
    List<Sort.Order> orders = new ArrayList<>();
    if (sorts[0].contains(COMMA)) {
      // will sort more than 2 fields
      for (String sortOrder : sorts) {
        String[] _sort = sortOrder.split(COMMA);
        orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
      }
    } else {
      // sort=[field, direction]
      orders.add(new Sort.Order(getSortDirection(sorts[1]), sorts[0]));
    }

    return PageRequest.of(page, size, Sort.by(orders));
  }

  public List<EventModel> getEventByTimeRange(
      final String userId,
      LocalDateTime startTime,
      LocalDateTime endTime,
      int page,
      int size,
      String[] sort) {
    Optional<CustomerModel> userModelOptional = customerRepository.findById(Long.valueOf(userId));
    Pageable pagingSort = constructPageable(page, size, sort);

    Page<EventModel> eventPage =
        eventRepository.findByEventTime(
            userModelOptional
                .map(CustomerModel::getCustomerAgency)
                .map(AgencyModel::getAgencyId)
                .orElse(""),
            startTime.atOffset(OffsetDateTime.now().getOffset()),
            endTime.atOffset(OffsetDateTime.now().getOffset()),
            pagingSort);
    return eventPage.getContent();
  }

  private Sort.Direction getSortDirection(String direction) {
    return Optional.ofNullable(direction)
        .filter(value -> DESC.equalsIgnoreCase(direction))
        .map(value -> Sort.Direction.DESC)
        .orElse(Sort.Direction.ASC);
  }
}
