package com.shop.framework.rabbitmq;

public interface MessageSender {

    void send(MqMessage mqMessage);

}
