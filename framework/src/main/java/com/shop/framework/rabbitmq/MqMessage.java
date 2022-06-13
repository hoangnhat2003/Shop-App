package com.shop.framework.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MqMessage {

    private String exchange;

    private String routingKey;

    private Object message;
}