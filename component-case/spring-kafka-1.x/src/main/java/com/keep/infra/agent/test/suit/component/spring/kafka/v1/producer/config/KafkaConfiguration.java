package com.keep.infra.agent.test.suit.component.spring.kafka.v1.producer.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
@Configuration("producerConfiguration")
public class KafkaConfiguration {

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>(16);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "dev-kafka-01.dev.keep:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, "3");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "gzip");
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, "200");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

}
