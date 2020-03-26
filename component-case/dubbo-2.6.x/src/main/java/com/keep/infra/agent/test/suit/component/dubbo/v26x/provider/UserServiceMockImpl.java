package com.keep.infra.agent.test.suit.component.dubbo.v26x.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.keep.infra.agent.test.suit.component.dubbo.api.UserInfo;
import com.keep.infra.agent.test.suit.component.dubbo.api.UserService;
import org.springframework.stereotype.Component;

/**
 * @author yunhai.hu
 * at 2020/3/12
 */
@Service()
@Component
public class UserServiceMockImpl implements UserService {
    @Override
    public UserInfo findUser(String userName) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId((long) 10);
        userInfo.setUserName("Tom Hanks");
        userInfo.setSex(0);
        userInfo.setMobile("18888888888");
        userInfo.setAddr("USA");
        return userInfo;
    }
}
