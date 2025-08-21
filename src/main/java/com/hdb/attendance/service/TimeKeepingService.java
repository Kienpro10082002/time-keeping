package com.hdb.attendance.service;

import com.hdb.attendance.application.constants.Constant;
import com.hdb.attendance.application.resources.request.DataHeaderReq;
import com.hdb.attendance.application.resources.request.TimeKeepingReq;
import com.hdb.attendance.application.resources.response.TimeKeepingRes;
import com.hdb.attendance.application.utils.DateUtils;
import com.hdb.attendance.application.utils.JsonLogUtil;
import com.hdb.attendance.application.configuration.ApplicationConfig;
import com.hdb.attendance.infra.TimeKeepingClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimeKeepingService {

    private static final Logger logger = LoggerFactory.getLogger(TimeKeepingService.class);

    private final TimeKeepingClient client;
    private final ApplicationConfig appConfig;
    private final LoginService loginService;

    public void checkInJob() {
        try {
            logger.info("Start calling CHECK IN...");

            TimeKeepingReq request = buildTimeKeepingReqBody(appConfig.getStatus().getCheckInFlag());

            TimeKeepingRes response = client.execute(request);

            JsonLogUtil.logJson(logger, response, "📦 Response for check in");
        } catch (Exception e) {
            logger.error("Error calling CHECK IN", e);
        }
    }

    public void checkOutJob() {
        try {
            logger.info("Start calling CHECK OUT...");

            TimeKeepingReq request = buildTimeKeepingReqBody(appConfig.getStatus().getCheckOutFlag());

            TimeKeepingRes response = client.execute(request);

            JsonLogUtil.logJson(logger, response, "📦 Response for check out");
        } catch (Exception e) {
            logger.error("Error calling CHECK OUT", e);
        }
    }

    private TimeKeepingReq buildTimeKeepingReqBody(String flag) {
        return TimeKeepingReq.builder()
                .stoken(loginService.login()) //call login api to get Stoken
                .appVersion(Constant.APP_VERSION)
                .os(appConfig.getDevice().getOs())
                .dataHeaderRequest(DataHeaderReq.builder()
                        .wifiName(appConfig.getWifi().getName())
                        .wifiPassword(appConfig.getWifi().getPassword())
                        .wifiIp(appConfig.getWifi().getIp())
                        .P6("")
                        .symbol(flag)
                        .requestTime(DateUtils.getNowInVietnam())
                        .deviceMac(appConfig.getDevice().getMac())
                        .build())
                .company(appConfig.getCompany().getName())
                .langID(appConfig.getCompany().getLanguage())
                .build();
    }

}
