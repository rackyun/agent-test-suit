package com.keep.infra.agent.test.suit.component.hbase.v2.controller;

import com.keep.infra.agent.test.suit.component.hbase.v2.service.HBaseTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("hbaseV2Controller")
@RequestMapping("/test/hbase/v2")
public class UserInfoController {

    @Autowired
    private HBaseTestService hBaseTestService;

    @GetMapping(value = "/run")
    public String testHbase(@RequestParam String name) {
        hBaseTestService.prepare();
        try {
            hBaseTestService.test();
        } catch (Exception e) {
            e.printStackTrace();
            return "NO";
        }
        return "OK";
    }

}
