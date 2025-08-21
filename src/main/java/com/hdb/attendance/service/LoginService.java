package com.hdb.attendance.service;

import com.hdb.attendance.application.configuration.ApplicationConfig;
import com.hdb.attendance.application.resources.request.LoginReq;
import com.hdb.attendance.infra.TimeKeepingClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private final TimeKeepingClient client;
    private final ApplicationConfig appConfig;

    public String login() {
        logger.info("Start calling LOGIN...");
        LoginReq reqBody = buildLoginReqBody();

        String stoken = client.login(reqBody).getMessage();

        return stoken == null ? appConfig.getApi().getStoken() : stoken;
    }

    private LoginReq buildLoginReqBody() {
        return LoginReq.builder()
                .LangID(appConfig.getCompany().getLanguage())
                .company(appConfig.getCompany().getName())

                .DeviceName(appConfig.getDevice().getName())
                .DeviceToke(appConfig.getDevice().getToken())
                .DeviceID(appConfig.getDevice().getId())

                .username(appConfig.getUser().getName())
                .Password(appConfig.getUser().getPassword())
                .build();
    }

}
