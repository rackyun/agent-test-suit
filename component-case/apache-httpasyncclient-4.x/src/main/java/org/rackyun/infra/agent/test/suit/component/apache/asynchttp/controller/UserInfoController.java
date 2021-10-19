package org.rackyun.infra.agent.test.suit.component.apache.asynchttp.controller;

import org.apache.http.NameValuePair;
import org.rackyun.infra.agent.test.suit.component.apache.asynchttp.util.HttpAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("asyncHttpController")
@RequestMapping("/test/apacheasynchttp/v4")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @GetMapping(value = "/run")
    public String testApacheHttp(@RequestParam String name) {
        List<NameValuePair> params = new ArrayList<>();
        try {
            logger.info(HttpAsyncClient.get("http://localhost:2015/apacheasynchttp/v4", params));
        } catch (IOException e) {
            e.printStackTrace();
            return "NOT";
        }
        return "OK";
    }

}
