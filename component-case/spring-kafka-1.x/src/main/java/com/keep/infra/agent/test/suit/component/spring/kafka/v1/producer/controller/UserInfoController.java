package com.keep.infra.agent.test.suit.component.spring.kafka.v1.producer.controller;

import com.keep.infra.agent.test.suit.component.spring.kafka.v1.producer.service.KafkaService;
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
@RestController("springKafkaV1Controller")
@RequestMapping("/test/springKafka/v1")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private KafkaService kafkaService;

    @GetMapping(value = "/run")
    public String testOkHttp(@RequestParam String name) {
        kafkaService.sendMessage(String.format("user %s request", name));
        return "OK";
    }

}
