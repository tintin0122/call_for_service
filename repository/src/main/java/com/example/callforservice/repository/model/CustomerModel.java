package com.example.callforservice.repository.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "customer")
public class CustomerModel {

  @Id
  @SequenceGenerator(name = "customer_id_seq", sequenceName = "customer_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_seq")
  private Long customerId;

  @Column private String customerName;

  @ManyToOne
  @JoinColumn(name = "agency_id", nullable = false)
  private AgencyModel customerAgency;


  @OneToMany(mappedBy = "customerModel")
  private Set<EventModel> events;
}
