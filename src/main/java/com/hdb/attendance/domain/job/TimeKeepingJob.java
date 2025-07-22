package com.hdb.attendance.domain.job;

import com.hdb.attendance.application.resources.request.DataHeaderReq;
import com.hdb.attendance.application.resources.request.TimeKeepingReq;
import com.hdb.attendance.application.resources.response.TimeKeepingRes;
import com.hdb.attendance.application.utils.JsonLogUtil;
import com.hdb.attendance.configuration.ApplicationConfig;
import com.hdb.attendance.infrastructure.client.TimeKeepingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TimeKeepingJob {

    private static final Logger logger = LoggerFactory.getLogger(TimeKeepingJob.class);

    public static final String ASIA_HO_CHI_MINH = "Asia/Ho_Chi_Minh";
    public static final String APP_VERSION = "PNJ_20210105_V1";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String CHECK_IN_TIME = "0 30 7 ? * MON-FRI";    // 07:30 từ Thứ 2 đến Thứ 6
    public static final String CHECK_OUT_TIME = "0 0 18 ? * MON-FRI";   // 18:00 từ Thứ 2 đến Thứ 6

    private final TimeKeepingService clientService;
    private final ApplicationConfig appConfig;

    @Scheduled(cron = CHECK_IN_TIME, zone = ASIA_HO_CHI_MINH) // chạy lúc 7:00 sáng giờ Việt Nam
    public void checkInJob() {
        try {
            logger.info("Start calling CHECK IN...");

            TimeKeepingReq request = TimeKeepingReq.builder()
                    .stoken(appConfig.getApi().getStoken())
                    .appVersion(APP_VERSION)
                    .os(appConfig.getDevice().getOs())
                    .dataHeaderRequest(DataHeaderReq.builder()
                            .wifiName(appConfig.getWifi().getName())
                            .wifiPassword(appConfig.getWifi().getPassword())
                            .wifiIp(appConfig.getWifi().getIp())
                            .P6("")
                            .symbol(appConfig.getStatus().getCheckInFlag())
                            .requestTime(getNowInVietnam())
                            .deviceMac(appConfig.getDevice().getMac())
                            .build())
                    .company(appConfig.getCompany().getName())
                    .langID(appConfig.getCompany().getLanguage())
                    .build();

            TimeKeepingRes response = clientService.execute(request);

            JsonLogUtil.logJson(logger, response, "📦 Response for check in");
        } catch (Exception e) {
            logger.error("Error calling CHECK IN", e);
        }
    }

    @Scheduled(cron = CHECK_OUT_TIME, zone = ASIA_HO_CHI_MINH) // chạy lúc 18:00 sáng giờ Việt Nam
    public void checkOutJob() {
        try {
            logger.info("Start calling CHECK OUT...");

            TimeKeepingReq request = TimeKeepingReq.builder()
                    .stoken(appConfig.getApi().getStoken())
                    .appVersion(APP_VERSION)
                    .os(appConfig.getDevice().getOs())
                    .dataHeaderRequest(DataHeaderReq.builder()
                            .wifiName(appConfig.getWifi().getName())
                            .wifiPassword(appConfig.getWifi().getPassword())
                            .wifiIp(appConfig.getWifi().getIp())
                            .P6("")
                            .symbol(appConfig.getStatus().getCheckOutFlag())
                            .requestTime(getNowInVietnam())
                            .deviceMac(appConfig.getDevice().getMac())
                            .build())
                    .company(appConfig.getCompany().getName())
                    .langID(appConfig.getCompany().getLanguage())
                    .build();

            TimeKeepingRes response = clientService.execute(request);

            JsonLogUtil.logJson(logger, response, "📦 Response for check out");
        } catch (Exception e) {
            logger.error("Error calling CHECK OUT", e);
        }
    }

    public static String getNowInVietnam() {
        return ZonedDateTime.now(ZoneId.of(ASIA_HO_CHI_MINH)).format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
    }

}
