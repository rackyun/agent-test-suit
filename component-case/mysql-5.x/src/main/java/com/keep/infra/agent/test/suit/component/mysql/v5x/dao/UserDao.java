package com.keep.infra.agent.test.suit.component.mysql.v5x.dao;

import com.keep.infra.agent.test.suit.component.mysql.v5x.module.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
@Repository
public class UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User query(String name) {
        List<User> users = jdbcTemplate.query("select * from user_info where user_name='" + name + "'", new User());
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }

    public void insert(User user) {
        jdbcTemplate.update("insert into user_info (user_name, sex, phone) value (?,?,?)", user.getName(),
                user.getSex(), user.getPhone());
    }

    public void update(User user) {
        jdbcTemplate.update("update user_info set phone=? where id=?", user.getPhone(), user.getId());
    }
}
