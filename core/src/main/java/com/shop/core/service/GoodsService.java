package com.shop.core.service;

import com.shop.core.dto.GoodsVO;
import com.shop.core.entity.Goods;
import com.shop.core.repository.GoodsRepo;
import com.shop.core.utils.CachePrefix;
import com.shop.core.utils.JsonParser;
import com.shop.framework.rabbitmq.RabbitMQInit;
import com.shop.framework.redis.RedisCache;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@Log4j
public class GoodsService {

    @Autowired
    private GoodsRepo goodsRepo;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private RedisCache redisCache;

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

        redisCache.put(CachePrefix.GOODS.getPrefix() + goods.getId(), JsonParser.toJson(goods));

//        Send message to RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQInit.QUEUE, goods);
        log.info("Sending Message to the Queue : " + goods.toString());
        return goods;
    }

    public Goods getGoodsById(Long id) {

        Goods goods = null;

        String goodsFromCache = (String) redisCache.get(CachePrefix.GOODS.getPrefix() + id);

        if (StringUtils.hasText(goodsFromCache)) {
            goods = (Goods) JsonParser.toObject(goodsFromCache, Goods.class);
            return goods;
        }

        try {

            goods = goodsRepo.findById(id).get();

            if (goods == null) {
                throw new Exception("Goods not found");
            }

            redisCache.put(CachePrefix.GOODS.getPrefix() + id, JsonParser.toJson(goods));

            return goods;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public Goods editGoods(Long goodsId, GoodsVO goodsVO) {

        Goods goods = null;

        String goodsFromCache = (String) redisCache.get(CachePrefix.GOODS.getPrefix() + goodsId);

        if (StringUtils.hasText(goodsFromCache)) {

            try {
                goods = (Goods) JsonParser.toObject(goodsFromCache, Goods.class);

                if (goods == null) {
                    throw new Exception("Goods not found");
                }

                goods = Goods.builder()
                        .goodsName(goodsVO.getGoodsName())
                        .brandId(goodsVO.getBrandId())
                        .categoryId(goodsVO.getCategoryId())
                        .commentNum(goodsVO.getCommentNum())
                        .build();

                Goods goodsUpdated = goodsRepo.save(goods);

                if (goodsUpdated != null) {
                    redisCache.remove(CachePrefix.GOODS.getPrefix() + goodsId);
                }

                redisCache.put(CachePrefix.GOODS.getPrefix() + goodsUpdated.getId(), goodsUpdated);

                return goodsUpdated;

            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        goods = Goods.builder()
                .goodsName(goodsVO.getGoodsName())
                .brandId(goodsVO.getBrandId())
                .categoryId(goodsVO.getCategoryId())
                .commentNum(goodsVO.getCommentNum())
                .build();

        Goods goodsUpdated = goodsRepo.save(goods);

        redisCache.put(CachePrefix.GOODS.getPrefix() + goodsUpdated.getId(), goodsUpdated);

        return goodsUpdated;
    }
}
