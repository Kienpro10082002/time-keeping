package com.hdb.attendance.application.resources.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataItem implements Serializable {

    private String checkTime;

}
