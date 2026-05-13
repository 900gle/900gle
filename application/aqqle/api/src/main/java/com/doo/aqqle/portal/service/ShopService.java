package com.doo.aqqle.portal.service;


import com.doo.aqqle.HostUrl;
import com.doo.aqqle.component.TextEmbedding;
import com.doo.aqqle.component.query.LocationSearchQuery;
import com.doo.aqqle.component.query.ShopSearchQuery;
import com.doo.aqqle.component.response.DataResponse;
import com.doo.aqqle.dto.TextEmbeddingDTO;
import com.doo.aqqle.model.CommonResult;
import com.doo.aqqle.model.request.LocationRequest;
import com.doo.aqqle.model.request.ShopRequest;
import com.doo.aqqle.service.ResponseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {

    private final ResponseService responseService;
    private final ShopSearchQuery query;
    private final LocationSearchQuery locationQuery;
    private final DataResponse response;
    private static final String SIMILARITY_ENABLED = "Y";
    private static final String DISTANCE = "5";
    private static final String COUNTRY_CODE = "Y";
    private static final Integer SIZE = 10;

    public CommonResult getProducts(ShopRequest request) {
        try {
            SearchSourceBuilder searchSourceBuilder = initializeSearchSourceBuilder();
            Script script = SIMILARITY_ENABLED.equals(request.getSimilarity()) ? createSimilarityScript(request.getSearchWord()) : getDefaultScript();
            FunctionScoreQueryBuilder functionScoreQueryBuilder = buildFunctionScoreQuery(script, request);

            LocationRequest locationRequest = new LocationRequest();
            locationRequest.setDistance(DISTANCE);
            locationRequest.setCountryCode(COUNTRY_CODE);
            locationRequest.setSize(SIZE);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("LOCATION", response.dataResponse(locationQuery.getDistanceBuilder(locationRequest), locationRequest));
            resultMap.put("ITEM", response.shopDataResponse(functionScoreQueryBuilder, request));

            return responseService.getSingleResult(resultMap);

        } catch (IOException e) {
            log.error("Error while fetching products: {}", e.getMessage(), e);
            return responseService.getFailResult();
        }
    }

    private SearchSourceBuilder initializeSearchSourceBuilder() {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.fetchSource(new String[]{}, new String[]{"feature_vector"});
        return builder;
    }

    private Script createSimilarityScript(String searchWord) throws IOException {
        Vector<Double> vectors = TextEmbedding.getVector(
                TextEmbeddingDTO.builder()
                        .tensorApiUrl(HostUrl.EMBEDDING.getUrl())
                        .keyword(searchWord).build()
        );
        Map<String, Object> params = new HashMap<>();
        params.put("query_vector", vectors);
        return new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, "cosineSimilarity(params.query_vector, 'feature_vector') + 1.0", params);
    }

    private Script getDefaultScript() {
        return new Script(ScriptType.INLINE, Script.DEFAULT_SCRIPT_LANG, "1", new HashMap<>());
    }

    private FunctionScoreQueryBuilder buildFunctionScoreQuery(Script script, ShopRequest request) {
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = query.getShopFunctionScoreQueryBuilder(script);
        return new FunctionScoreQueryBuilder(
                query.getShopQueryBuilder(request.getSearchWord()),
                filterFunctionBuilders
        );
    }
}
