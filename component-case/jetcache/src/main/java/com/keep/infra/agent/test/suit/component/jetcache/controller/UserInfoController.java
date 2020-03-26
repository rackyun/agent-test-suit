package com.keep.infra.agent.test.suit.component.jetcache.controller;

import com.keep.infra.agent.test.suit.component.jetcache.service.JetcacheTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("jetcacheController")
@RequestMapping("/test/jetcache")
public class UserInfoController {

    @Autowired
    private JetcacheTestService jetcacheTestService;

    @GetMapping(value = "/run")
    public String testCache(@RequestParam String name) {
        try {
            return jetcacheTestService.getTime(name);
        } catch (Exception e) {
            e.printStackTrace();
            return "NO";
        }
    }

}
