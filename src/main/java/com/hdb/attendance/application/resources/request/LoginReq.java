package com.hdb.attendance.application.resources.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginReq {
    public String LangID;
    public String Version = "17.6.1";
    public String build = "10";
    public String company;
    public String DeviceName;
    public String username;
    public String Password;
    public String OS = "2";
    public String DeviceToke;
    public String DeviceID;
}
