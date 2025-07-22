package com.hdb.attendance.application.resources.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TimeKeepingRes implements Serializable {

    private Boolean commit;

    private String code;

    @JsonProperty("langID")
    private String langId;

    private String message;

    private Integer count;

    private Info info;

}
