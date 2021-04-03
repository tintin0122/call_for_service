package com.example.callforservice.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Event {
    private String eventId;
    private Integer eventNumber;
    private String eventTypeCode;
    private LocalDateTime eventTime;
    private LocalDateTime dispatchTime;
    private Customer customer;
    private Responder responder;
}
