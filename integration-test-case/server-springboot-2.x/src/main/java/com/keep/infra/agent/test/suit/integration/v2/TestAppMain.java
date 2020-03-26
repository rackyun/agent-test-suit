package com.keep.infra.agent.test.suit.integration.v2;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author yunhai.hu
 * at 2018/11/27
 */
@SpringBootApplication(exclude = {EmbeddedMongoAutoConfiguration.class},
scanBasePackages = {"com.keep.infra.agent.test.suit.component"})
@EnableElasticsearchRepositories(basePackages = {"com.keep.infra.agent.test.suit.component"})
@ImportResource(value = {"classpath:applicationContext-core.xml", "classpath:applicationContext-jdbc.xml"})
@PropertySource(value = {"classpath:bootstrap.properties"})
public class TestAppMain {

    public static void main(String[] args) throws Exception {
        new SpringApplicationBuilder(TestAppMain.class).run(args);
    }
}
