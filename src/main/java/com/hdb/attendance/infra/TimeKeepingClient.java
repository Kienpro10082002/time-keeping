package com.hdb.attendance.infra;

import com.hdb.attendance.application.constants.Constant;
import com.hdb.attendance.application.resources.request.LoginReq;
import com.hdb.attendance.application.resources.request.TimeKeepingReq;
import com.hdb.attendance.application.resources.response.LoginRes;
import com.hdb.attendance.application.resources.response.TimeKeepingRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "TimeKeepingServiceImpl", url = "${hdb.api.base-url}")
public interface TimeKeepingClient {

    @PostMapping(Constant.TIME_KEEPING_ENDPOINT)
    TimeKeepingRes execute(@RequestBody TimeKeepingReq request);

    @PostMapping(Constant.LOGIN_ENDPOINT)
    LoginRes login(@RequestBody LoginReq request);

}
