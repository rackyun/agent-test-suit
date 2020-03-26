package com.keep.infra.agent.test.suit.component.elasticsearch.v6.module;


import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @author yunhai.hu
 * at 2019/1/16
 */
@Document(indexName = "testuser", type = "user")
public class EsUser implements Serializable {

    private Long id;
    private String name;
    private String sex;

    public EsUser() {
    }

    public EsUser(Long id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "EsUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
