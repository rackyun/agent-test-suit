package com.keep.infra.agent.test.suit.component.mongodb.v3.controller;

import com.keep.infra.agent.test.suit.component.mongodb.v3.dao.UsersRepository;
import com.keep.infra.agent.test.suit.component.mongodb.v3.module.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("mongoV3Controller")
@RequestMapping("/test/mongo/v3")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = "/run")
    public String testApacheHttp(@RequestParam String name) {

        try {
            List<User> users = usersRepository.getByName(name);
            if (users == null || users.isEmpty()) {
                User user = new User();
                user.setName(name);
                user.setSex(name.hashCode() % 2 == 0 ? "male" : "female");
                usersRepository.save(user);
            }
        } catch (Exception e) {
            logger.error("save error", e);
            return "NOT";
        }
        return "OK";
    }

}
