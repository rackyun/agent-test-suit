package org.rackyun.infra.agent.test.suit.component.dubbo.api;

/**
 * @author yunhai.hu
 * at 2020/3/12
 */
public interface UserService {

    UserInfo findUser(String userName);
}
