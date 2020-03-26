package com.keep.infra.agent.test.suit.component.okhttp3.controller;

import com.keep.infra.agent.test.suit.component.okhttp3.service.TestWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("okhttpV3Controller")
@RequestMapping("/test/okhttp/v3")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private TestWebService testWebService;

    @GetMapping(value = "/run")
    public String testOkHttp(@RequestParam String name) {
        logger.info(testWebService.catNodes());
        return "OK";
    }

}
