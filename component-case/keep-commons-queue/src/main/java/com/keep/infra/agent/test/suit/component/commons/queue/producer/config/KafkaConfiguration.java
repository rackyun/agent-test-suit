package com.keep.infra.agent.test.suit.component.commons.queue.producer.config;

import com.keep.commons.queue.kafka.producer.KProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yunhai.hu
 * at 2020/4/23
 */
@Configuration
public class KafkaConfiguration {

    @Value("${kafka.brokers}")
    private String broker;

    @Bean
    public KProducer getKProducer() {
        return new KProducer(broker);
    }
}
