package com.bbongdoo.doo.service;

import com.bbongdoo.doo.component.ImageToVectorOpenCV;
import com.bbongdoo.doo.dto.VectorDTO;
import com.bbongdoo.doo.model.response.CommonResult;
import com.etoos.imagesearch.dto.ImageSearchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScriptScoreQueryBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpencvImageSearchService {

    @Value("${file.dir.temp}")
    private String tempDir;

    private final ResponseService responseService;
    private final RestHighLevelClient client;
    private final String ALIAS = "shop";

    public CommonResult getImages(ImageSearchDTO imageSearchDTO) {

        try {
            Vector<Double> vectors = ImageToVectorOpenCV.getVector(
                    VectorDTO.builder()
                            .dirPath(tempDir)
                            .file(
                                    imageSearchDTO.getFile()
                            ).build()
            );

            SearchRequest searchRequest = new SearchRequest();
            searchRequest.indices(ALIAS);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

//            String[] includeFields = new String[]{"image_id", "image_name"};
//            String[] excludeFields = new String[]{"feature"};
//            searchSourceBuilder.fetchSource(includeFields, excludeFields);

            Map<String, Object> map = new HashMap<>();
            map.put("imageVector", vectors);

            ScriptScoreQueryBuilder functionScoreQueryBuilder = new ScriptScoreQueryBuilder(
                    QueryBuilders.matchAllQuery(),
                    new Script(
                            Script.DEFAULT_SCRIPT_TYPE,
                            Script.DEFAULT_SCRIPT_LANG,
                            "cosineSimilarity(params.imageVector, 'image_vector') + 1.0", map)
            );

            searchSourceBuilder.query(functionScoreQueryBuilder);
            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            List<Map<String, Object>> returnValue = new ArrayList<>();
            SearchHit[] results = searchResponse.getHits().getHits();
            Arrays.stream(results).forEach(hit -> {
                Map<String, Object> result = hit.getSourceAsMap();
                result.put("score", hit.getScore() - 1.0);
                returnValue.add(result);
            });

            return responseService.getListResult(returnValue);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseService.getFailResult();

    }


}
