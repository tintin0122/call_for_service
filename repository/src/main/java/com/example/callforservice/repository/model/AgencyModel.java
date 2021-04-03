package com.example.callforservice.repository.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "agency")
public class AgencyModel {

  @Id private String agencyId;

  @Column private String agencyName;

  @OneToMany(mappedBy = "responderAgency", cascade = CascadeType.ALL)
  private Set<ResponderModel> responders;

  @OneToMany(mappedBy = "customerAgency")
  private Set<CustomerModel> users;
}
