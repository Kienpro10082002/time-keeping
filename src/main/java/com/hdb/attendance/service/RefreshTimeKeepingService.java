package com.hdb.attendance.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class RefreshTimeKeepingService {

    /*
🔹 Nếu app start lại lúc 15h → vẫn chạy check-out bình thường, check-in bị bỏ qua.
🔹 Nếu start lúc 8h sáng → vẫn chạy check-in và check-out hôm đó.
🔹 Nếu start lúc 19h tối → bỏ cả hai, chờ tới 00:00 random lại cho ngày mai.
    * */

    private static final Logger logger = LoggerFactory.getLogger(RefreshTimeKeepingService.class);
    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    private final TimeKeepingService timeKeepingService;
    private TaskScheduler scheduler;

    @PostConstruct
    public void init() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.initialize();
        this.scheduler = taskScheduler;

        scheduleDailyJobs();
    }

    private void scheduleDailyJobs() {
        // Lập lịch lại mỗi ngày vào 00:00
        scheduler.scheduleAtFixedRate(this::scheduleRandomCheckTimes, Date.from(
                LocalDateTime.now(VIETNAM_ZONE)
                        .withHour(0).withMinute(0).withSecond(0).plusDays(1)
                        .atZone(VIETNAM_ZONE).toInstant()
        ), 24 * 60 * 60 * 1000);

        // Lập lịch cho hôm nay ngay khi start
        scheduleRandomCheckTimes();
    }

    private void scheduleRandomCheckTimes() {
        Random random = new Random();
        LocalDateTime now = LocalDateTime.now(VIETNAM_ZONE);

        // Random check-in: 7:30 - 7:45
        int checkInMinute = 30 + random.nextInt(16);
        LocalDateTime checkInTime = now.withHour(7).withMinute(checkInMinute).withSecond(0);

        if (checkInTime.isAfter(now)) {
            scheduler.schedule(timeKeepingService::checkInJob,
                    Date.from(checkInTime.atZone(VIETNAM_ZONE).toInstant()));
            logger.info("Scheduled CHECK IN at {}", checkInTime);
        } else {
            logger.info("Skip CHECK IN because time has passed for today");
        }

        // Random check-out: 17:45 - 18:00
        int checkOutMinute = 45 + random.nextInt(16);
        LocalDateTime checkOutTime = now.withHour(17).withMinute(checkOutMinute).withSecond(0);

        if (checkOutTime.isAfter(now)) {
            scheduler.schedule(timeKeepingService::checkOutJob,
                    Date.from(checkOutTime.atZone(VIETNAM_ZONE).toInstant()));
            logger.info("Scheduled CHECK OUT at {}", checkOutTime);
        } else {
            logger.info("Skip CHECK OUT because time has passed for today");
        }
    }

}

