package com.shop.core.service;

import com.shop.core.dto.GoodsVO;
import com.shop.core.entity.Goods;
import com.shop.core.rabbitmq.Exchange;
import com.shop.core.rabbitmq.message.GoodsChangeMsg;
import com.shop.core.repository.GoodsRepo;
import com.shop.framework.rabbitmq.MessageSender;
import com.shop.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GoodsService {

    @Autowired
    private GoodsRepo goodsRepo;

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private GoodsSkuService goodsSkuService;

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

        goodsSkuService.add(goodsVO.getSkuList(), goods);

//        Send message to RabbitMQ

        GoodsChangeMsg goodsChangeMsg = GoodsChangeMsg.builder()
                .goodsId(goods.getId())
                .operationType(GoodsChangeMsg.ADD_OPERATION)
                .build();

        MqMessage mqMessage = new MqMessage(Exchange.GOODS_CHANGE, Exchange.GOODS_CHANGE + "_ROUTING", goodsChangeMsg);

        messageSender.send(mqMessage);

        return goods;
    }

}
