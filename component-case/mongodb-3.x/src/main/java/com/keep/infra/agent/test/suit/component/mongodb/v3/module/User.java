package com.keep.infra.agent.test.suit.component.mongodb.v3.module;


import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author yunhai.hu
 * at 2019/1/16
 */
public class User implements Serializable {

    @Id
    private String id;
    private String name;
    private String sex;

    public User() {
    }

    public User(String id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
