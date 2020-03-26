package com.keep.infra.agent.test.suit.component.elasticsearch.v6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yunhai.hu
 * at 2019/1/17
 */
@SpringBootApplication(exclude = {MongoDataAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class,
        MongoAutoConfiguration.class})
@PropertySource(value = {"classpath:application.properties", "classpath:bootstrap.properties"})
public class TestAppMain {
    static {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }
    public static void main(String[] args) {
//        SpringApplication springApplication = new SpringApplication(TestAppMain.class);
//        springApplication.setWebApplicationType(WebApplicationType.SERVLET);
//        springApplication.run(args);
        SpringApplication.run(TestAppMain.class, args);
    }
}
