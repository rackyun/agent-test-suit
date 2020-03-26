package com.keep.infra.agent.test.suit.component.mongodb.v3.dao;

import com.keep.infra.agent.test.suit.component.mongodb.v3.module.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yunhai.hu
 * at 2020/3/16
 */
@Repository("mongoUsersRepository")
public class UsersRepositoryImpl implements UsersRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<User> getByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public void save(User user) {
        if (user.getId() != null) {
            Query query = new Query(Criteria.where("id").is(user.getId()));
            Update update = new Update().set("name", user.getName()).set("sex", user.getSex());
            mongoTemplate.updateFirst(query, update, User.class);
        } else {
            mongoTemplate.save(user);
        }
    }
}
