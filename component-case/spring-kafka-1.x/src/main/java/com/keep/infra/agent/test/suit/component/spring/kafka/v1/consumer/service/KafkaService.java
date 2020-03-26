package com.keep.infra.agent.test.suit.component.spring.kafka.v1.consumer.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
@Service("kafkaConsumerService")
public class KafkaService implements MessageListener<String, String> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @KafkaListener(topics = {"agent-test"}, group = "test.consumer")
    @Override
    public void onMessage(ConsumerRecord<String, String> record) {
        logger.info("received message {}", record.value());
    }
}
