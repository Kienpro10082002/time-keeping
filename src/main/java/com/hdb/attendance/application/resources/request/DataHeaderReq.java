package com.hdb.attendance.application.resources.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DataHeaderReq {
    @JsonProperty("P5")
    private String wifiName;

    @JsonProperty("P0")
    private String wifiPassword;

    @JsonProperty("P3")
    private String wifiIp;

    @JsonProperty("P6")
    private String P6;

    @JsonProperty("P1")
    private String symbol;

    @JsonProperty("P4")
    private String requestTime;

    @JsonProperty("P2")
    private String deviceMac;

}
