package com.example.callforservice.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {
    private Long customerId;
    private String customerName;
    private Agency agency;
}
