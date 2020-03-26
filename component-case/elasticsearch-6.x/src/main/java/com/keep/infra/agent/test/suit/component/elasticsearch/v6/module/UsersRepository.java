package com.keep.infra.agent.test.suit.component.elasticsearch.v6.module;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yunhai.hu
 * at 2019/1/16
 */
@Component("esUsersRepository")
public interface UsersRepository extends ElasticsearchRepository<EsUser, Long> {

    List<EsUser> getByName(String name);
}
