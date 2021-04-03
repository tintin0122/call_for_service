package com.example.callforservice.repository.model;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "event")
public class EventModel {
  @Id private String eventId;

  @Column private Integer eventNumber;

  @Column private String eventTypeCode;

  @Column private OffsetDateTime eventTime;

  @Column private OffsetDateTime dispatchTime;

  @ManyToOne
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerModel customerModel;

  @ManyToOne
  @JoinColumn(name = "responder_id")
  private ResponderModel responder;
}
