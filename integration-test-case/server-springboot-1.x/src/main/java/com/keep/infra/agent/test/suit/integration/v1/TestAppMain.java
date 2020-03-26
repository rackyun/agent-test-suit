package com.keep.infra.agent.test.suit.integration.v1;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yunhai.hu
 * at 2018/11/27
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class, KafkaAutoConfiguration.class},
scanBasePackages = {"com.keep.infra.agent.test.suit.component"})
@ImportResource(value = {"classpath:applicationContext-core.xml", "classpath:applicationContext-jdbc.xml"})
@PropertySource(value = {"classpath:bootstrap.properties"})
public class TestAppMain {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(TestAppMain.class).run(args);
    }
}
