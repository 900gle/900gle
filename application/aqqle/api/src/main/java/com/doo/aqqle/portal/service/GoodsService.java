package com.doo.aqqle.portal.service;


import com.doo.aqqle.HostUrl;
import com.doo.aqqle.component.TextEmbedding;
import com.doo.aqqle.component.query.ShopSearchQuery;
import com.doo.aqqle.dto.TextEmbeddingDTO;
import com.doo.aqqle.enums.ElasticStatic;
import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.model.request.ShopRequest;
import com.doo.aqqle.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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
public class GoodsService {

    private final ResponseService responseService;
    private final RestHighLevelClient client;
    private final ShopSearchQuery shopSearchQuery;

    public CommonResult getProducts(ShopRequest request) {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ElasticStatic.SHOP.getAlias());
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            String[] includeFields = new String[]{};
            String[] excludeFields = new String[]{"feature_vector"};
            searchSourceBuilder.fetchSource(includeFields, excludeFields);

            FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = null;

            Script script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, "1", new HashMap<>());

            if ("Y".equals(request.getSimilarity())) {
                Vector<Double> vectors = TextEmbedding.getVector(
                        TextEmbeddingDTO.builder()
                                .tensorApiUrl(HostUrl.EMBEDDING.getUrl())
                                .keyword(request.getSearchWord()).build()
                );
                Map<String, Object> map = new HashMap<>();
                map.put("query_vector", vectors);
                script = new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, "cosineSimilarity(params.query_vector, 'feature_vector') + 1.0", map);
            }

            filterFunctionBuilders = shopSearchQuery.getShopFunctionScoreQueryBuilder(script);

            FunctionScoreQueryBuilder functionScoreQueryBuilder = new FunctionScoreQueryBuilder(
                    shopSearchQuery.getShopQueryBuilder(request.getSearchWord()),
                    filterFunctionBuilders
            );

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

            return responseService.getListResult(returnValue);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommonResult();
    }

}
