package com.shop.core.service;

import com.shop.core.dto.GoodsVO;
import com.shop.core.entity.Goods;
import com.shop.core.repository.GoodsRepo;
import com.shop.framework.rabbitmq.RabbitMQInit;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Log4j
public class GoodsService {

    @Autowired
    private GoodsRepo goodsRepo;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public Goods add(GoodsVO goodsVO) {

        Goods goods = new Goods(goodsVO);

        goods.setCreatedDate(new Date());

        // Number of product views
        goods.setViewCount(0);
        // Quantity of goods purchased
        goods.setBuyCount(0);
        // Comment number
        goods.setCommentNum(0);

        goods.setQuantity(goodsVO.getQuantity() == null ? 0 : goodsVO.getQuantity());

        // Available in stock
        goods.setEnableQuantity(goods.getQuantity());

        goods = goodsRepo.save(goods);

//        Send message to RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQInit.QUEUE, goods);
        log.info("Sending Message to the Queue : " + goods.toString());
        return goods;
    }

}
