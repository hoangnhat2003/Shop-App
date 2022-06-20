package com.shop.core.entity;

import com.shop.core.dto.GoodsVO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Table(name = "goods")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Goods {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goods_name")
    private String goodsName;

    @Column(name = "brand_id")
    private Integer brandId;

    private String brandName;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "goods_type")
    private String goodsType;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "price")
    private Double price;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "buy_count")
    private Integer buyCount;

    @Column(name = "disabled")
    private Boolean disabled;

    @Column(name = "comment_num")
    private Integer commentNum;

    @Column(name = "quantity")
    private Integer quantity;

    /**
     * Available
     */
    @Column(name = "enable_quantity")
    private Integer enableQuantity;

    private Date createdDate;

    public Goods(GoodsVO goodsVO) {
        this.goodsName = goodsVO.getGoodsName();
        this.brandId = goodsVO.getBrandId();
        this.categoryId = goodsVO.getCategoryId();
        this.price = goodsVO.getPrice();
        this.weight = goodsVO.getWeight();
        this.quantity = goodsVO.getQuantity();
    }



}
