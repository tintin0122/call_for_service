package com.example.callforservice.repository.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "responder")
public class ResponderModel {

  @Id
  @SequenceGenerator(
      name = "responder_id_seq",
      sequenceName = "responder_id_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "responder_id_seq")
  private Long responderId;

  @Column private String responderCode;

  @Column private String responderName;

  @OneToMany(mappedBy = "responder")
  private Set<EventModel> events;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "agency_id", nullable = false)
  private AgencyModel responderAgency;
}
