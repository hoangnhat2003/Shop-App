package com.shop.framework.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public class MessageImpl implements MessageSender{

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void send(MqMessage mqMessage) {

        publisher.publishEvent(mqMessage);

    }
}
