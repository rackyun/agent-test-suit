package com.keep.infra.agent.test.suit.component.tomcat.v78.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("tomcatV78Controller")
@RequestMapping("/test/tomcat/v78")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @GetMapping(value = "/run")
    public String testTomcat(@RequestParam String name) {
        logger.info("request user {}", name);
        return "OK";
    }

}
