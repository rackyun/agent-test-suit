package com.keep.infra.agent.test.suit.component.dubbo.v26x.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.keep.infra.agent.test.suit.component.dubbo.api.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunhai.hu
 * at 2020/3/12
 */
@RestController("dubboV26Controller")
@RequestMapping("/test/dubbo/v26")
public class UserInfoController {

    @Reference(check = false)
    private UserService userService;

    @GetMapping(value = "/run")
    public String queryUserInfo(@RequestParam String name) {
        userService.findUser(name);
        return "OK";
    }
}
