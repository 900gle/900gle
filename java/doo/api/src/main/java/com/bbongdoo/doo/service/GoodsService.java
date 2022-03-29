package com.bbongdoo.doo.service;

import com.bbongdoo.doo.component.TextEmbedding;
import com.bbongdoo.doo.dto.TextEmbeddingDTO;
import com.bbongdoo.doo.model.response.CommonResult;
import com.google.common.base.Functions;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.common.lucene.search.function.ScoreFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.*;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static java.util.Collections.emptyMap;

@Service
@RequiredArgsConstructor
public class GoodsService {

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

//            String[] includeFields = new String[]{"name", "category"};
            String[] includeFields = new String[]{};
            String[] excludeFields = new String[]{"feature_vector"};
            searchSourceBuilder.fetchSource(includeFields, excludeFields);

            Map<String, Object> map = new HashMap<>();
            map.put("query_vector", vectors);

            FunctionScoreQueryBuilder functionScoreQueryBuilder = new FunctionScoreQueryBuilder(
                    QueryBuilders.boolQuery().must(
                            QueryBuilders.multiMatchQuery(searchWord, "name", "category")
                    ).should(
                            QueryBuilders.multiMatchQuery(searchWord, "category1", "category2", "category3", "category4", "category5")
                    ),
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                            new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                    new ScriptScoreFunctionBuilder(
                                            new Script(
                                                    ScriptType.INLINE,
                                                    Script.DEFAULT_SCRIPT_LANG,
                                                    "cosineSimilarity(params.query_vector, 'feature_vector') * doc['weight'].value * doc['popular'].value / doc['name.keyword'].length + doc['category.keyword'].length",
                                                    map)
                                    ).setWeight(0.1f)
                            )
                    }
            );

            searchSourceBuilder.query(functionScoreQueryBuilder);
            searchSourceBuilder.size(8);
            searchRequest.source(searchSourceBuilder);

            System.out.println(searchRequest.source());
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
