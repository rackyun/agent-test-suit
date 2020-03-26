package com.keep.infra.agent.test.suit.component.dubbo.api;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yunhai.hu
 * at 2020/3/12
 */
@Data
public class UserInfo implements Serializable {

    private Long id;
    private String userName;
    private int sex;
    private String addr;
    private String mobile;
}
