package com.keep.infra.agent.test.suit.component.elasticsearch.v5.module;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yunhai.hu
 * at 2019/1/16
 */
@Component("esUsersRepository")
public interface UsersRepository extends ElasticsearchRepository<User, Long> {

    List<User> getByName(String name);
}
