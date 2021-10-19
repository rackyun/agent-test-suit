package org.rackyun.infra.agent.test.suit.component.apache.http.controller;

import org.apache.http.NameValuePair;
import org.rackyun.infra.agent.test.suit.component.apache.http.util.HttpClient;
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
@RestController("httpController")
@RequestMapping("/test/apachehttp/v4")
public class UserInfoController {

    @GetMapping(value = "/run")
    public String testApacheHttp(@RequestParam String name) {
        List<NameValuePair> params = new ArrayList<>();
        try {
            HttpClient.get("http://localhost:2015/apachehttp/v4", params);
        } catch (IOException e) {
            e.printStackTrace();
            return "NOT";
        }
        return "OK";
    }

}
