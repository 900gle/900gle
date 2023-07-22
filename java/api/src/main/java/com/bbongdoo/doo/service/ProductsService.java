package com.bbongdoo.doo.service;

import com.bbongdoo.doo.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ResponseService responseService;
    private final RestHighLevelClient client;
    private final String ALIAS = "shop";

    public CommonResult getProducts(String searchWord){

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ALIAS);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(QueryBuilders.termQuery("name", searchWord));
        boolQueryBuilder.should(QueryBuilders.matchQuery("name", searchWord));
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(8);
        searchRequest.source(searchSourceBuilder);

        System.out.println(searchRequest);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            List<Map<String, Object>> returnValue = new ArrayList<>();
            SearchHit[] results = searchResponse.getHits().getHits();

            Arrays.stream(results).forEach(hit -> {
                Map<String, Object> result = hit.getSourceAsMap();
                System.out.println(result.get("name"));
                returnValue.add(result);
            });

            return responseService.getListResult(returnValue);

        } catch (IOException e) {
            e.printStackTrace();
        }
        

        return new CommonResult();
    }
}
