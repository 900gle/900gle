package com.doo.aqqle.portal.service;


import com.doo.aqqle.common.CacheKey;
import com.doo.aqqle.enums.ElasticStatic;
import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final ResponseService responseService;
    private final RestHighLevelClient client;

    @Cacheable(value = CacheKey.LOCATION, key = "#countryCode", unless = "#result == null")
    public CommonResult getLocations(String countryCode) {

        try {

            SearchRequest aggsRequest = new SearchRequest();
            aggsRequest.indices(ElasticStatic.LOCATION.getAlias());

            SearchSourceBuilder aggsSourceBuilder = new SearchSourceBuilder();
            QueryBuilder aggsQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("country_code", countryCode));
            aggsSourceBuilder.query(aggsQueryBuilder);
            aggsSourceBuilder.size(0);

            List<AggregationBuilder> aggregationBuilders = new ArrayList<>();

            AggregationBuilder countryBuilder = AggregationBuilders.terms("COUNTRY").field("country").subAggregation(AggregationBuilders.terms("CITY").field("city"));
            aggregationBuilders.add(countryBuilder);
            AggregationBuilder cityBuilder = AggregationBuilders.terms("CITY").field("city").subAggregation(AggregationBuilders.terms("ADDR").field("addr1"));
            aggregationBuilders.add(cityBuilder);

            for (AggregationBuilder aggs : aggregationBuilders){
                aggsSourceBuilder.aggregation(aggs);
            }

            aggsRequest.source(aggsSourceBuilder);
            System.out.println(aggsSourceBuilder);
            SearchResponse aggsResponse = client.search(aggsRequest, RequestOptions.DEFAULT);

            List<Map<String, Object>> aggsValue = new ArrayList<>();
            SearchHit[] aggsResults = aggsResponse.getHits().getHits();

            Arrays.stream(aggsResults).forEach(hit -> {
                Map<String, Object> result = hit.getSourceAsMap();
                result.put("score", hit.getScore());
                aggsValue.add(result);
            });


            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(ElasticStatic.LOCATION.getAlias());

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            String[] includeFields = new String[]{};
            String[] excludeFields = new String[]{};
            searchSourceBuilder.fetchSource(includeFields, excludeFields);

            QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("country_code", countryCode));
            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.size(20);

            searchRequest.source(searchSourceBuilder);
            System.out.println(searchSourceBuilder);
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            List<Map<String, Object>> returnValue = new ArrayList<>();
            SearchHit[] results = searchResponse.getHits().getHits();

            Arrays.stream(results).forEach(hit -> {
                Map<String, Object> result = hit.getSourceAsMap();
                result.put("score", hit.getScore());
                returnValue.add(result);
            });
            return responseService.getListResult(returnValue);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return new CommonResult();
    }

}
