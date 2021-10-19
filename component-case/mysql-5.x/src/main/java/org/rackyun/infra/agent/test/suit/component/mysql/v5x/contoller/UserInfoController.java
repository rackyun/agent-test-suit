package org.rackyun.infra.agent.test.suit.component.mysql.v5x.contoller;

import org.rackyun.infra.agent.test.suit.component.mysql.v5x.dao.UserDao;
import org.rackyun.infra.agent.test.suit.component.mysql.v5x.module.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author yunhai.hu
 * at 2020/3/13
 */
@RestController("mysqlController")
@RequestMapping("/test/mysql/v5")
public class UserInfoController {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
    private final Random random = new Random();

    @Autowired
    private UserDao userDao;

    @GetMapping(value = "/run")
    public String testApacheHttp(@RequestParam String name) {

        try {
            User user = userDao.query(name);
            if (user == null) {
                user = new User();
                user.setName(name);
                user.setSex(name.hashCode() % 2);
                user.setPhone("1888888888");
                userDao.insert(user);
            } else {
                user.setPhone("188888888" + random.nextInt(10));
                userDao.update(user);
            }
        } catch (Exception e) {
            logger.error("save error", e);
            return "NOT";
        }
        return "OK";
    }

}
