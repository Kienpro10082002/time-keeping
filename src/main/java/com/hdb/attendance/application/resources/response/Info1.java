package com.hdb.attendance.application.resources.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Info1 implements Serializable {

    private Integer countItem;

    @JsonProperty("dataItem")
    private List<DataItem> dataItem;

}
