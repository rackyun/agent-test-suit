package com.keep.infra.agent.test.suit.component.jedis.v2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("jedisV2Controller")
@RequestMapping("/test/jedis/v2")
public class UserInfoController {

    private static final String redis_key = "jhm_test_string";
    private static final String redis_value = "8285a306-8810-405e-b459-470300361687";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping(value = "/run")
    public String testJedis(@RequestParam String name) {
        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            String val = valueOperations.get(redis_key);
            if (StringUtils.isEmpty(val)) {
                valueOperations.set(redis_key, redis_value);
            } else {
//                redisTemplate.delete(redis_key);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "NO";
        }
        return "OK";
    }

}
