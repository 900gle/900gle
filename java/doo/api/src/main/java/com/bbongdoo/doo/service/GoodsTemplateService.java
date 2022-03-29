package com.bbongdoo.doo.service;

import com.bbongdoo.doo.component.TextEmbedding;
import com.bbongdoo.doo.dto.TextEmbeddingDTO;
import com.bbongdoo.doo.model.response.CommonResult;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScriptScoreQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class GoodsTemplateService {

    private final ResponseService responseService;
    private final RestHighLevelClient client;
    private final String ALIAS = "goods";

    public CommonResult getProducts(String searchWord) {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ALIAS);
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            Vector<Double> vectors = TextEmbedding.getVector(
                    TextEmbeddingDTO.builder()
                            .tensorApiUrl("http://localhost:5000/vectors")
                            .keyword(searchWord).build()
            );

            SearchTemplateRequest request = new SearchTemplateRequest();
            request.setRequest(new SearchRequest(ALIAS));

            request.setScriptType(ScriptType.STORED);
            request.setScript("goods-search-template");

            Map<String, Object> params = new HashMap<>();
            params.put("query", searchWord);
            params.put("query_vector", vectors);
            params.put("from", 0);
            params.put("size", 5);
            request.setScriptParams(params);
            request.setExplain(true);
            request.setProfile(true);

            System.out.println(request.getRequest());

            SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);

            List<Map<String, Object>> returnValue = new ArrayList<>();
            SearchHit[] results = response.getResponse().getHits().getHits();

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
