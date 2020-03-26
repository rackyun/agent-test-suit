package com.keep.infra.agent.test.suit.component.jetcache;

import com.keep.spring.boot.cache.EnableKeepCacheConfiguration;
import com.keep.spring.boot.codis.EnableKeepCodisConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yunhai.hu
 * @date 2018/10/29
 */
@Slf4j
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class, DataSourceAutoConfiguration.class,
        ElasticsearchAutoConfiguration.class})
@PropertySource({"classpath:bootstrap.properties", "classpath:codis.properties"})
@EnableKeepCodisConfiguration
@EnableKeepCacheConfiguration
public class TestAppMain {

    public static void main(String[] args) {
        SpringApplication.run(TestAppMain.class, args);
    }
}
