package com.keep.infra.agent.test.suit.component.commons.queue.producer.service;

import com.keep.commons.queue.kafka.producer.KProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @author yunhai.hu
 * at 2020/3/17
 */
@Service("commonsQueueProducerService")
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);
    private static final String topic = "agent-test";

    @Autowired
    private KProducer producer;

    public void sendMessage(String message) {
        Future<RecordMetadata> future = producer.send(topic, message);
        try {
            RecordMetadata recordMetadata = future.get();
        } catch (Exception e) {
            logger.error("send message error", e);
        }
    }
}
