package com.shop.core.searchgoods.service;

import com.shop.core.entity.Goods;
import com.shop.core.searchgoods.model.ElasticGoods;
import com.sun.istack.NotNull;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class InitIndex {

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    @RabbitListener(queues = "queueName")
    public void receiver(Goods goods) {

        log.info("Goods from queue : " + goods.toString());

        ElasticGoods elasticGoods = ElasticGoods.builder()
                .goodsId(goods.getId())
                .goodsName(goods.getGoodsName())
                .brandId(goods.getBrandId())
                .categoryId(goods.getBrandId())
                .price(goods.getPrice())
                .commentNum(goods.getCommentNum())
                .weight(goods.getWeight())
                .quantity(goods.getQuantity())
                .categoryName(goods.getGoodsName())
                .build();

        this.indexAppUser(elasticGoods);
    }

    public void indexAppUser(@NotNull ElasticGoods elasticGoods) {
        String documentId = elasticGoods.getGoodsId().toString();
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(documentId)
                .withObject(elasticGoods)
                .build();
        elasticsearchOperations.index(indexQuery, IndexCoordinates.of(queueName));
    }

}
