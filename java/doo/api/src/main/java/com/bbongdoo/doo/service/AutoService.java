package com.bbongdoo.doo.service;

import com.bbongdoo.doo.component.TextEmbedding;
import com.bbongdoo.doo.dto.TextEmbeddingDTO;
import com.bbongdoo.doo.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AutoService {

    private final ResponseService responseService;
    private final RestHighLevelClient client;
    private final String ALIAS = "auto";

    public CommonResult getProducts(String searchWord) {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ALIAS);
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            QueryBuilder queryBuilder =
                    QueryBuilders.boolQuery().must(
                            QueryBuilders.multiMatchQuery(searchWord, "name.jamo", "name.chosung","name.eng2kor", "name.kor2eng","name.spell", "name.keyword")
                    );

            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.size(10);
            searchRequest.source(searchSourceBuilder);

            System.out.println(searchRequest.source());
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            List<Map<String, Object>> returnValue = new ArrayList<>();
            SearchHit[] results = searchResponse.getHits().getHits();

            Arrays.stream(results).forEach(hit -> {
                Map<String, Object> result = hit.getSourceAsMap();
                returnValue.add(result);
            });

            return responseService.getListResult(returnValue);

        } catch (IOException e) {
            e.printStackTrace();
        }


        return new CommonResult();
    }

}
