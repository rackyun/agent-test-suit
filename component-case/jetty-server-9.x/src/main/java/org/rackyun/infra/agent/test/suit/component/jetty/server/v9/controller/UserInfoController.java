package org.rackyun.infra.agent.test.suit.component.jetty.server.v9.controller;

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
@RestController("jettyServerController")
@RequestMapping("/test/jettyServer/v3")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @GetMapping(value = "/run")
    public String testJettyServer(@RequestParam String name) {
        logger.info("request user {}", name);
        return "OK";
    }

}
