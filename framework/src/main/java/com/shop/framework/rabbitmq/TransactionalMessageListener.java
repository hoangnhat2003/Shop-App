package com.shop.framework.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TransactionalMessageListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @TransactionalEventListener(fallbackExecution = true)
    public void handlePublishMessageMq(MqMessage mqMessage) {
         rabbitTemplate.convertAndSend(mqMessage.getExchange(), mqMessage.getRoutingKey(), mqMessage.getMessage());
    }
}
