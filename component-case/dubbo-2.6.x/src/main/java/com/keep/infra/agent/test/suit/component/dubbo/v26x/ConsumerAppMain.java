package com.keep.infra.agent.test.suit.component.dubbo.v26x;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yunhai.hu
 * at 2020/3/12
 */
@SpringBootApplication
@EnableDubbo
@PropertySource(value = {"classpath:bootstrap.properties", "classpath:application.properties"})
public class ConsumerAppMain {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerAppMain.class, args);
    }
}
