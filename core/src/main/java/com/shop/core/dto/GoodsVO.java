package com.shop.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class GoodsVO {

    private Integer goodsId;
    private Integer categoryId;

    /**
     * Used to display
     */
    private String categoryName;

    private Integer brandId;

    private String goodsName;

    private Double price;

    private Double weight;

    private Integer quantity;

    private Integer enableQuantity;

    private Integer commentNum;

}
