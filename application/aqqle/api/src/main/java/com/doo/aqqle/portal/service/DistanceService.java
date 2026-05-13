package com.doo.aqqle.portal.service;


import com.doo.aqqle.common.CacheKey;
import com.doo.aqqle.enums.ElasticStatic;
import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.model.request.LocationRequest;
import com.doo.aqqle.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DistanceService {

    private final ResponseService responseService;
    private final RestHighLevelClient client;

    @Cacheable(value = CacheKey.DISTANCE, key = "T(com.doo.aqqle.utils.KeyGenerator).cacheKey('getDistance', #request.distance, #request.countryCode)")
    public CommonResult getDistance(LocationRequest request) {

        try {

            double lat =  37.48822057363974; //위도
            double lon =  126.90500268692999; //경도

            SearchRequest distanceRequest = new SearchRequest();
            distanceRequest.indices(ElasticStatic.LOCATION.getAlias());
            SearchSourceBuilder locationSourceBuilder = new SearchSourceBuilder();

            QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.geoDistanceQuery("location").point(lat, lon).distance( request.getDistance() +"km"))
                    .filter(QueryBuilders.termQuery("country_code", request.getCountryCode()));

            locationSourceBuilder.query(queryBuilder);
            locationSourceBuilder.from(request.getFrom());
            locationSourceBuilder.size(request.getSize());
            distanceRequest.source(locationSourceBuilder);

            SearchResponse aggsResponse = client.search(distanceRequest, RequestOptions.DEFAULT);
            List<Map<String, Object>> aggsValue = new ArrayList<>();
            SearchHit[] aggsResults = aggsResponse.getHits().getHits();
            Arrays.stream(aggsResults).forEach(hit -> {
                Map<String, Object> result = hit.getSourceAsMap();
                result.put("score", hit.getScore());
                aggsValue.add(result);
            });

            return responseService.getListResult(aggsValue);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseService.getFailResult();
    }

}
