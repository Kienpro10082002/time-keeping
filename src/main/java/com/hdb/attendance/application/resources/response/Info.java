package com.hdb.attendance.application.resources.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Info implements Serializable {

    @JsonProperty("info1")
    private Info1 info1;

}
