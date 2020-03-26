package com.keep.infra.agent.test.suit.component.monitor.v24.controller;

import com.keep.monitor.api.KMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("monitorV24Controller")
@RequestMapping("/test/monitor/v24")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @GetMapping(value = "/run")
    public String testMonitor(@RequestParam String name) {
        logger.info("request user {}", name);

        KMonitor.getKeepMetrics().probe().tag("monitor", "v24").counter("custom.monitor.invoke").inc();

        KMonitor.getKeepMetrics().probe().tag("method", "testMethod")
                .timer("custom.monitor.request").update(100, TimeUnit.MILLISECONDS);
//        testMethod(name);
        return "OK";
    }

    private void testMethod(String name) {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
