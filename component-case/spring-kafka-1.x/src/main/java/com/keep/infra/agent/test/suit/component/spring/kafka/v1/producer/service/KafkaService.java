package com.keep.infra.agent.test.suit.component.spring.kafka.v1.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
@Service("kafkaProducerService")
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
    private static final String topic = "agent-test";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        try {
            SendResult<String, String> result = future.get();
        } catch (Exception e) {
            logger.error("send message error", e);
        }
    }
}
