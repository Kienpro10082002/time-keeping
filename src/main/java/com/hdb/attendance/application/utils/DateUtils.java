package com.hdb.attendance.application.utils;

import com.hdb.attendance.application.constants.Constant;
import lombok.experimental.UtilityClass;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtils {
    public static String getNowInVietnam() {
        return ZonedDateTime.now(ZoneId.of(Constant.ASIA_HO_CHI_MINH)).format(DateTimeFormatter.ofPattern(Constant.YYYY_MM_DD_HH_MM_SS));
    }
}
