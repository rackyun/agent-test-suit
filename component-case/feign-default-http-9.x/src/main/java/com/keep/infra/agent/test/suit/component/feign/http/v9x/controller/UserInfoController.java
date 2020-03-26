package com.keep.infra.agent.test.suit.component.feign.http.v9x.controller;

import com.keep.infra.agent.test.suit.component.feign.http.v9x.service.TestWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("feignV9Controller")
@RequestMapping("/test/feign/v9")
public class UserInfoController {

    @Autowired
    private TestWebService testWebService;

    @GetMapping(value = "/run")
    public String testFeign(@RequestParam String name) {
        testWebService.catNodes();
        return "OK";
    }

}
