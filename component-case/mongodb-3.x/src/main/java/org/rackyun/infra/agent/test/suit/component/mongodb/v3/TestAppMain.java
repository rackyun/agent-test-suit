package org.rackyun.infra.agent.test.suit.component.mongodb.v3;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yunhai.hu
 * at 2018/11/27
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, KafkaAutoConfiguration.class})
@PropertySource(value = {"classpath:bootstrap.properties"})
public class TestAppMain {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(TestAppMain.class).run(args);
    }
}
