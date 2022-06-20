package com.shop.core.searchgoods.service;

import com.shop.core.searchgoods.model.ElasticGoods;
import com.shop.framework.elasticsearch.ElasticsearchConfig;
import lombok.extern.log4j.Log4j;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j
public class GoodsSearchService {

    @Autowired
    private ElasticsearchConfig esConfig;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    public Map<String, Object> searchGoods(Optional<String> goodsName,
                                           Optional<String> categoryName,
                                           Optional<String> brandName,
                                           String[] price,
                                           Integer pageNo,
                                           Integer pageSize) {

        List<ElasticGoods> data = new ArrayList<>();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if(!goodsName.isPresent()) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("goodsName", "*" + goodsName.get() + "*"));
        }

        if(!categoryName.isPresent()) {
            boolQueryBuilder.must(QueryBuilders.termQuery("categoryName", categoryName.get()));
        }

        if(!brandName.isPresent()) {
            boolQueryBuilder.must(QueryBuilders.termQuery("brandName", brandName.get()));
        }

        if(price != null && price.length > 0 && price.length < 3) {
            double min = this.toDouble(price[0]);
            double max = this.toDouble(price[1]);

            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").from(min).to(max));
        }

        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withFilter(boolQueryBuilder)
                .withSearchType(SearchType.DEFAULT)
                .withPageable(PageRequest.of(pageNo -1 , pageSize))
                .withSorts(SortBuilders.fieldSort("createdDate").order(SortOrder.DESC))
                .build();

        SearchHits<ElasticGoods> searchHits = elasticsearchOperations.search(query, ElasticGoods.class, IndexCoordinates.of(esConfig.getIndex()));

        if (searchHits.isEmpty()) {
            return Collections.singletonMap("Info", "Value empty");
        }

        try {
            data = searchHits.getSearchHits().stream()
                    .map(SearchHit::getContent)
                    .collect(Collectors.toList());

            if(data == null) {
                log.error("Data from elasticsearch query is null");
                throw new Exception("Data from elasticsearch query is null");
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("totalElements", searchHits.getTotalHits());

        return map;
    }

    private Double toDouble(String value)  {

        Double defaultValue = 0d;

        if(value == null || "".equals(value)) {
            return defaultValue;
        }

        defaultValue = Double.valueOf(value);

        return defaultValue;
    }


}
