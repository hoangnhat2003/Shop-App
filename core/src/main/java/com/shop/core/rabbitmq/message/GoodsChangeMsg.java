package com.shop.core.rabbitmq.message;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoodsChangeMsg {

    private Long goodsId;

    private Integer operationType;

    private String message;


    /**
     * add
     */
    public final static int ADD_OPERATION = 1;

    /**
     * Manually modify
     */
    public final static int MANUAL_UPDATE_OPERATION = 2;

    /**
     * Automatically change
     */
    public final static int AUTO_UPDATE_OPERATION = 9;

    /**
     * delete
     */
    public final static int DEL_OPERATION = 3;





}
