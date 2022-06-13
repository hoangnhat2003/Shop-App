package com.shop.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Table(name = "goods_sku")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GoodsSku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goods_id")
    private Long goodsId;
    /**
     * Name
     */
    @Column(name = "goods_name")
    private String goodsName;


    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "enable_quantity")
    private Integer enableQuantity;


    @Column(name = "price")
    private Double price;

    @Column(name = "weight")
    private Double weight;
    /**
     * The sellerid
     */
    private Integer sellerId;
    /**
     * The seller name
     */
    private String sellerName;
    /**
     * Categoriesid
     */
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "hash_code")
    private Integer hashCode;
}
