package com.example.callforservice.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Getter
@Setter
public class ResponseEvent {
    @JsonProperty("agency_id")
    private String agencyId;

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("event_number")
    private Integer eventNumber;

    @JsonProperty("event_type_code")
    private String eventTypeCode;

    @JsonProperty("event_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime eventTime;

    @JsonProperty("dispatch_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime dispatchTime;

    @JsonProperty("responder")
    private String responder;
}
