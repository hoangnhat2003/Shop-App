package com.shop.core.searchgoods.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Builder
public class ElasticGoods {

    @Id
    @Field(name = "goodsId",type = FieldType.Long)
    private Long goodsId;

    @Field(name = "categoryId", type = FieldType.Integer)
    private Integer categoryId;

    /**
     * Used to display
     */
    @Field(name = "categoryName", type = FieldType.Text)
    private String categoryName;

    @Field(name = "brandId", type = FieldType.Integer)
    private Integer brandId;

    @Field(name = "brandName", type = FieldType.Text)
    private String brandName;

    @Field(name = "goodsName", type = FieldType.Text )
    private String goodsName;

    @Field(name = "price", type = FieldType.Double)
    private Double price;

    @Field(name = "weight", type = FieldType.Double)
    private Double weight;

    @Field(name = "quantity", type = FieldType.Integer)
    private Integer quantity;

    @Field(name = "commentNum", type = FieldType.Integer)
    private Integer commentNum;

    @Field(name = "createdDate", type = FieldType.Date)
    private Date createdDate;

}
