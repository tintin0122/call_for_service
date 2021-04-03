package com.example.callforservice.repository;

import com.example.callforservice.repository.model.EventModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface EventRepository extends JpaRepository<EventModel, String> {

  @Query(
      "SELECT evt FROM EventModel evt"
          + " JOIN CustomerModel cus ON evt.customerModel.customerId = cus.customerId"
          + " JOIN AgencyModel agency ON cus.customerAgency.agencyId = agency.agencyId"
          + " WHERE agency.agencyId = :agencyId"
          + " AND evt.eventTime >= :startTime AND evt.eventTime <= :endTime")
  Page<EventModel> findByEventTime(
      String agencyId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable pageable);

  @Query(
      "SELECT evt FROM EventModel evt"
          + " INNER JOIN ResponderModel res ON evt.responder.responderId = res.responderId"
          + " WHERE res.responderCode = :responderCode")
  Page<EventModel> findByResponder(String responderCode, Pageable pageable);
}
