package com.shop.core.dto;

import com.shop.core.entity.GoodsSku;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoodsSkuVO extends GoodsSku {


    private Integer disabled;

    private String goodsType;

    private Long lastModify;

}
