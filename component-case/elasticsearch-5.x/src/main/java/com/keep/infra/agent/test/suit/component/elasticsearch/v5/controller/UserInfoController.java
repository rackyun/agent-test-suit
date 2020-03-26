package com.keep.infra.agent.test.suit.component.elasticsearch.v5.controller;

import com.keep.infra.agent.test.suit.component.elasticsearch.v5.module.User;
import com.keep.infra.agent.test.suit.component.elasticsearch.v5.module.UsersRepository;
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
@RestController("elasticsearchV5Controller")
@RequestMapping("/test/elastic/v5")
public class UserInfoController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping(value = "/run")
    public String testElastic(@RequestParam String name) {
        List<User> result = usersRepository.getByName(name);
        if (result != null && !result.isEmpty()) {
            return "OK";
        }
        User user = new User();
        user.setName(name);
        user.setSex(name.hashCode() % 2 == 0 ? "male" : "female");
        usersRepository.save(user);
        return "OK";
    }
}
