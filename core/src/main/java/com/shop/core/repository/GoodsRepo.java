package com.shop.core.repository;

import com.shop.core.entity.Goods;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepo extends CrudRepository<Goods, Long> {
}
