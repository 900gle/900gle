package com.doo.aqqle.component.response;

import com.doo.aqqle.common.CacheKey;
import com.doo.aqqle.enums.ElasticStatic;
import com.doo.aqqle.model.request.LocationRequest;
import com.doo.aqqle.model.request.ShopRequest;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.SearchHit;
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
public class DataResponse {
    private final RestHighLevelClient client;

    public List<Map<String, Object>> shopDataResponse(FunctionScoreQueryBuilder functionScoreQueryBuilder, ShopRequest request) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticStatic.SHOP.getAlias());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        String[] includeFields = new String[]{};
        String[] excludeFields = new String[]{"feature_vector"};
        searchSourceBuilder.fetchSource(includeFields, excludeFields);
        searchSourceBuilder.query(functionScoreQueryBuilder);
        searchSourceBuilder.from(request.getFrom());
        searchSourceBuilder.size(request.getSize());

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

        return returnValue;
    }
    @Cacheable(value = CacheKey.DISTANCE, key = "T(com.doo.aqqle.utils.KeyGenerator).cacheKey('getDistance', #request.distance, #request.countryCode)")
    public List<Map<String, Object>> dataResponse(QueryBuilder queryBuilder, LocationRequest request) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticStatic.LOCATION.getAlias());
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(request.getFrom());
        searchSourceBuilder.size(request.getSize());

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

        return returnValue;
    }
}
