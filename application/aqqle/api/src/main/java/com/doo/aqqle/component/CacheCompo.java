package com.doo.aqqle.component;

import com.doo.aqqle.common.CacheKey;
import com.doo.aqqle.enums.ElasticStatic;
import com.doo.aqqle.model.CacheResponse;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CacheCompo {

    private final RestHighLevelClient client;

//    @Cacheable(value = CacheKey.NAME, key = "#countryCode", unless = "#result == null")
    public CacheResponse getCacheName (String countryCode) throws IOException {
        System.out.println("cacheName");

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
//        System.out.println(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> returnValue = new ArrayList<>();
        SearchHit[] results = searchResponse.getHits().getHits();

        Arrays.stream(results).forEach(hit -> {
            Map<String, Object> result = hit.getSourceAsMap();
            result.put("score", hit.getScore());
            returnValue.add(result);
        });

        CacheResponse cacheResponse = new CacheResponse();
        cacheResponse.setName("cacheName");
        return cacheResponse;
    }

//    @Cacheable(value = CacheKey.FILTER, key = "#countryCode", unless = "#result == null")
    public CacheResponse getCacheFilter (String countryCode) throws IOException{
        System.out.println("cacheFilter");

        SearchRequest aggsRequest = new SearchRequest();
        aggsRequest.indices(ElasticStatic.LOCATION.getAlias());

        SearchSourceBuilder aggsSourceBuilder = new SearchSourceBuilder();
        QueryBuilder aggsQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("country_code", countryCode));
        aggsSourceBuilder.query(aggsQueryBuilder);
        aggsSourceBuilder.size(0);

        List<AggregationBuilder> aggregationBuilders = new ArrayList<>();

        AggregationBuilder countryBuilder = AggregationBuilders.terms("CACHEFILTER_COUNTRY").field("country").subAggregation(AggregationBuilders.terms("CITY").field("city"));
        aggregationBuilders.add(countryBuilder);
        AggregationBuilder cityBuilder = AggregationBuilders.terms("CACHEFILTER_CITY").field("city");
        aggregationBuilders.add(cityBuilder);

        for (AggregationBuilder aggs : aggregationBuilders){
            aggsSourceBuilder.aggregation(aggs);
        }

        aggsRequest.source(aggsSourceBuilder);
//        System.out.println(aggsSourceBuilder);
        SearchResponse aggsResponse = client.search(aggsRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> aggsValue = new ArrayList<>();
        SearchHit[] aggsResults = aggsResponse.getHits().getHits();

        Arrays.stream(aggsResults).forEach(hit -> {
            Map<String, Object> result = hit.getSourceAsMap();
            result.put("score", hit.getScore());
            aggsValue.add(result);
        });

        CacheResponse cacheResponse = new CacheResponse();
        cacheResponse.setName("cacheFilter");
        return cacheResponse;
    }

    @Cacheable(value = CacheKey.ATTR, key = "#countryCode", unless = "#result == null")
    public CacheResponse getCacheAttr (String countryCode) throws IOException{
        System.out.println("cacheAttr");

        SearchRequest aggsRequest = new SearchRequest();
        aggsRequest.indices(ElasticStatic.LOCATION.getAlias());

        SearchSourceBuilder aggsSourceBuilder = new SearchSourceBuilder();
        QueryBuilder aggsQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("country_code", countryCode));
        aggsSourceBuilder.query(aggsQueryBuilder);
        aggsSourceBuilder.size(0);

        List<AggregationBuilder> aggregationBuilders = new ArrayList<>();

        AggregationBuilder countryBuilder = AggregationBuilders.terms("CACHEATTR_COUNTRY").field("country").subAggregation(AggregationBuilders.terms("CITY").field("city"));
        aggregationBuilders.add(countryBuilder);

        for (AggregationBuilder aggs : aggregationBuilders){
            aggsSourceBuilder.aggregation(aggs);
        }

        aggsRequest.source(aggsSourceBuilder);
//        System.out.println(aggsSourceBuilder);
        SearchResponse aggsResponse = client.search(aggsRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> aggsValue = new ArrayList<>();
        SearchHit[] aggsResults = aggsResponse.getHits().getHits();

        Arrays.stream(aggsResults).forEach(hit -> {
            Map<String, Object> result = hit.getSourceAsMap();
            result.put("score", hit.getScore());
            aggsValue.add(result);
        });

        CacheResponse cacheResponse = new CacheResponse();
        cacheResponse.setName("cacheAttr");
        return cacheResponse;
    }

    @Cacheable(value = CacheKey.AGGS, key = "#countryCode", unless = "#result == null")
    public CacheResponse getCacheAggs (String countryCode) throws IOException{
        System.out.println("cacheAggs");

        SearchRequest aggsRequest = new SearchRequest();
        aggsRequest.indices(ElasticStatic.LOCATION.getAlias());

        SearchSourceBuilder aggsSourceBuilder = new SearchSourceBuilder();
        QueryBuilder aggsQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("country_code", countryCode));
        aggsSourceBuilder.query(aggsQueryBuilder);
        aggsSourceBuilder.size(0);

        List<AggregationBuilder> aggregationBuilders = new ArrayList<>();

        AggregationBuilder countryBuilder = AggregationBuilders.terms("CACHEAGGS_COUNTRY").field("country").subAggregation(AggregationBuilders.terms("CITY").field("city"));
        aggregationBuilders.add(countryBuilder);
        AggregationBuilder cityBuilder = AggregationBuilders.terms("CACHEAGGS_CITY").field("city");
        aggregationBuilders.add(cityBuilder);

        for (AggregationBuilder aggs : aggregationBuilders){
            aggsSourceBuilder.aggregation(aggs);
        }

        aggsRequest.source(aggsSourceBuilder);
//        System.out.println(aggsSourceBuilder);
        SearchResponse aggsResponse = client.search(aggsRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> aggsValue = new ArrayList<>();
        SearchHit[] aggsResults = aggsResponse.getHits().getHits();

        Arrays.stream(aggsResults).forEach(hit -> {
            Map<String, Object> result = hit.getSourceAsMap();
            result.put("score", hit.getScore());
            aggsValue.add(result);
        });

        CacheResponse cacheResponse = new CacheResponse();
        cacheResponse.setName("cacheAggs");
        return cacheResponse;
    }

    @Cacheable(value = CacheKey.AVG, key = "#countryCode", unless = "#result == null")
    public CacheResponse getCacheAvg (String countryCode) throws IOException{
        System.out.println("cacheAvg");

        SearchRequest aggsRequest = new SearchRequest();
        aggsRequest.indices(ElasticStatic.LOCATION.getAlias());

        SearchSourceBuilder aggsSourceBuilder = new SearchSourceBuilder();
        QueryBuilder aggsQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("country_code", countryCode));
        aggsSourceBuilder.query(aggsQueryBuilder);
        aggsSourceBuilder.size(0);

        List<AggregationBuilder> aggregationBuilders = new ArrayList<>();

        AggregationBuilder countryBuilder = AggregationBuilders.terms("CACHEAVG_COUNTRY").field("country").subAggregation(AggregationBuilders.terms("CITY").field("city"));
        aggregationBuilders.add(countryBuilder);
        AggregationBuilder cityBuilder = AggregationBuilders.terms("CACHEAVG_CITY").field("city");
        aggregationBuilders.add(cityBuilder);

        for (AggregationBuilder aggs : aggregationBuilders){
            aggsSourceBuilder.aggregation(aggs);
        }

        aggsRequest.source(aggsSourceBuilder);
//        System.out.println(aggsSourceBuilder);
        SearchResponse aggsResponse = client.search(aggsRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> aggsValue = new ArrayList<>();
        SearchHit[] aggsResults = aggsResponse.getHits().getHits();

        Arrays.stream(aggsResults).forEach(hit -> {
            Map<String, Object> result = hit.getSourceAsMap();
            result.put("score", hit.getScore());
            aggsValue.add(result);
        });

        CacheResponse cacheResponse = new CacheResponse();
        cacheResponse.setName("cacheAvg");
        return cacheResponse;
    }

    @Cacheable(value = CacheKey.SUM, key = "#countryCode", unless = "#result == null")
    public CacheResponse getCacheSum (String countryCode) throws IOException{
        System.out.println("cacheSum");

        SearchRequest aggsRequest = new SearchRequest();
        aggsRequest.indices(ElasticStatic.LOCATION.getAlias());

        SearchSourceBuilder aggsSourceBuilder = new SearchSourceBuilder();
        QueryBuilder aggsQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery("country_code", countryCode));
        aggsSourceBuilder.query(aggsQueryBuilder);
        aggsSourceBuilder.size(0);

        List<AggregationBuilder> aggregationBuilders = new ArrayList<>();

        AggregationBuilder countryBuilder = AggregationBuilders.terms("CACHESUM_COUNTRY").field("country").subAggregation(AggregationBuilders.terms("CITY").field("city"));
        aggregationBuilders.add(countryBuilder);
        AggregationBuilder cityBuilder = AggregationBuilders.terms("CACHESUM_CITY").field("city");
        aggregationBuilders.add(cityBuilder);

        for (AggregationBuilder aggs : aggregationBuilders){
            aggsSourceBuilder.aggregation(aggs);
        }

        aggsRequest.source(aggsSourceBuilder);
//        System.out.println(aggsSourceBuilder);
        SearchResponse aggsResponse = client.search(aggsRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> aggsValue = new ArrayList<>();
        SearchHit[] aggsResults = aggsResponse.getHits().getHits();

        Arrays.stream(aggsResults).forEach(hit -> {
            Map<String, Object> result = hit.getSourceAsMap();
            result.put("score", hit.getScore());
            aggsValue.add(result);
        });

        CacheResponse cacheResponse = new CacheResponse();
        cacheResponse.setName("cacheSum");
        return cacheResponse;
    }
}

