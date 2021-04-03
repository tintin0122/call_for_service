package com.example.callforservice.repository.service;

import com.example.callforservice.repository.*;
import com.example.callforservice.repository.model.AgencyModel;
import com.example.callforservice.repository.model.EventModel;
import com.example.callforservice.repository.model.ResponderModel;
import com.example.callforservice.repository.model.CustomerModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {H2JpaConfig.class})
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionalTestExecutionListener.class
})
public class EventServiceTest {
  @InjectMocks private EventService eventService;

  @Autowired private ResponderRepository responderRepository;

  @Autowired private AgencyRepository agencyRepository;

  @Autowired private EventRepository eventRepository;

  @Autowired private CustomerRepository customerRepository;

  @Before
  public void init() {
    AgencyModel agencyModel = new AgencyModel();
    agencyModel.setAgencyId(UUID.randomUUID().toString());
    agencyModel.setAgencyName("Agency 01");
    this.agencyRepository.save(agencyModel);

    Optional<AgencyModel> agencyModelOptional =
        this.agencyRepository.findById(agencyModel.getAgencyId());

    ResponderModel responderModel = new ResponderModel();
    responderModel.setResponderCode("OFFICER_001");
    responderModel.setResponderName("officer 001");
    responderModel.setResponderAgency(agencyModelOptional.orElse(null));
    responderRepository.save(responderModel);

    CustomerModel customerModel = new CustomerModel();
    customerModel.setCustomerId((long) 1001);
    customerModel.setCustomerAgency(agencyModelOptional.orElse(null));
    customerModel.setCustomerName("user test");
    customerRepository.save(customerModel);

    Optional<CustomerModel> optionalCustomerModel =
        this.customerRepository.findById(customerModel.getCustomerId());

    EventModel eventModel01 = new EventModel();
    eventModel01.setEventId("900001");
    eventModel01.setEventNumber(3324);
    eventModel01.setEventTime(OffsetDateTime.now());
    eventModel01.setDispatchTime(OffsetDateTime.now());
    eventModel01.setEventTypeCode("CMO");
    eventModel01.setCustomerModel(optionalCustomerModel.orElse(null));
    eventModel01.setResponder(responderModel);

    EventModel eventModel02 = new EventModel();
    eventModel02.setEventId("900002");
    eventModel02.setEventNumber(3324);
    eventModel02.setEventTime(OffsetDateTime.now());
    eventModel02.setDispatchTime(OffsetDateTime.now());
    eventModel02.setEventTypeCode("CMO");
    eventModel02.setCustomerModel(optionalCustomerModel.orElse(null));
    eventModel02.setResponder(responderModel);
    eventRepository.saveAll(Arrays.asList(eventModel01, eventModel02));

    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(eventService, "eventRepository", eventRepository);
  }

  @Test
  public void testGetEventsByResponder() {
    List<EventModel> events =
        eventService.getEventsByResponder("OFFICER_001", 0, 20, new String[] {"eventTime,desc"});
    Assertions.assertTrue(events != null && events.size() == 2);
  }
}
