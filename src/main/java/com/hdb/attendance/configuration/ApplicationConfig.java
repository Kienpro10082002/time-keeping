package com.hdb.attendance.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hdb")
@Getter
@Setter
public class ApplicationConfig {

    private Status status = new Status();
    private Company company = new Company();
    private Attendance attendance = new Attendance();
    private Api api = new Api();
    private Wifi wifi = new Wifi();
    private Device device = new Device();

    @Getter
    @Setter
    public class Status {
        private String checkInFlag;
        private String checkOutFlag;
    }

    @Getter
    @Setter
    public class Company {
        private String name;
        private String language;
    }

    @Getter
    @Setter
    public class Attendance {
        private String enabled;
        private String cronCheckIn;
        private String cronCheckOut;
        private String batchSize;
        private String maxRetries;
        private String retryDelay;
    }

    @Getter
    @Setter
    public class Api {
        private String baseUrl;
        private String timeKeepingEndpoint;
        private String stoken;
    }

    @Getter
    @Setter
    public class Wifi {
        private String name;
        private String password;
        private String ip;
    }

    @Getter
    @Setter
    public class Device {
        private String deviceId;
        private String mac;
        private String os;
    }


}
