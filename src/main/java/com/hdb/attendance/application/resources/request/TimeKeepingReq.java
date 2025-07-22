package com.hdb.attendance.application.resources.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TimeKeepingReq {

    @JsonProperty("Stoken")
    private String stoken;

    @JsonProperty("AppVersion")
    private String appVersion;

    @JsonProperty("OS")
    private String os;

    @JsonProperty("DataHeader")
    private DataHeaderReq dataHeaderRequest;

    @JsonProperty("company")
    private String company;

    @JsonProperty("LangID")
    private String langID;


}
