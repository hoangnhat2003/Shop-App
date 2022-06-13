package com.shop.framework.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public interface MessageSender {

    void send(MqMessage mqMessage);

}
