package org.rackyun.infra.agent.test.suit.component.mysql.v5x.module;


import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yunhai.hu
 * at 2019/1/16
 */
@Data
public class User implements Serializable, RowMapper<User> {

    private Long id;
    private String name;
    private int sex;
    private String phone;

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("user_name"));
        user.setSex(resultSet.getInt("sex"));
        user.setPhone(resultSet.getString("phone"));
        return user;
    }
}
