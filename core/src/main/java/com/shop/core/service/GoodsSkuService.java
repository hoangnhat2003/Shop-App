package com.shop.core.service;

import com.shop.core.dto.GoodsSkuVO;
import com.shop.core.entity.Goods;
import com.shop.core.entity.GoodsSku;
import com.shop.core.repository.GoodsSkuRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GoodsSkuService {

    @Autowired
    private GoodsSkuRepo goodsSkuRepo;

    public void add(List<GoodsSkuVO> skuList, Goods goods) {

       if(skuList != null && skuList.size() > 0 ) {
           addGoodsSku(skuList, goods);
       }

    }

    private void addGoodsSku(List<GoodsSkuVO> skuList, Goods goods) {

        for (GoodsSkuVO skuVO : skuList) {
            add(skuVO, goods);
        }
    }

    private GoodsSku add(GoodsSkuVO skuVO, Goods goods) {

        skuVO.setGoodsId(goods.getId());
        GoodsSku sku = new GoodsSku();
        BeanUtils.copyProperties(skuVO, sku);

        sku.setEnableQuantity(sku.getQuantity());
        sku.setGoodsName(goods.getGoodsName());
        sku.setCategoryId(goods.getCategoryId());

        return goodsSkuRepo.save(sku);
    }

}
