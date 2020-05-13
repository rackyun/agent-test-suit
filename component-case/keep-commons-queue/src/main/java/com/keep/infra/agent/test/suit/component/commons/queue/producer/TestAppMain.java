package com.keep.infra.agent.test.suit.component.commons.queue.producer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

import java.net.URL;

/**
 * @author yunhai.hu
 * at 2018/11/27
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class, KafkaAutoConfiguration.class})
@PropertySource(value = {"classpath:bootstrap.properties"})
public class TestAppMain {

    public static void main(String[] args) throws Exception {
        URL url = TestAppMain.class.getClassLoader().getResource("META-INF/infra-log-config");
        System.out.println(url.getFile());
        new SpringApplicationBuilder(TestAppMain.class).run(args);
    }
}
