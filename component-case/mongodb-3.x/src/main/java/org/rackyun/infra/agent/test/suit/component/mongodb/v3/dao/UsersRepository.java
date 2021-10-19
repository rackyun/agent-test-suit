package org.rackyun.infra.agent.test.suit.component.mongodb.v3.dao;

import org.rackyun.infra.agent.test.suit.component.mongodb.v3.module.User;

import java.util.List;

/**
 * @author yunhai.hu
 * at 2019/1/16
 */
public interface UsersRepository {

    List<User> getByName(String name);

    public void save(User user);
}
