package com.doo.aqqle.service;


import com.doo.aqqle.dto.ExtractGoodsTextDTO;
import com.doo.aqqle.dto.GoodTextDTO;
import com.doo.aqqle.repository.Goods;
import com.doo.aqqle.repository.GoodsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.SpringTransactionAnnotationParser;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncTaskService {

    private final RestHighLevelClient client;


    private final GoodsRepository goodsRepository;

    @Async("executor")
    public CompletableFuture<Integer> task(String indexName, String file) throws FileNotFoundException, ParseException, IOException {

        JSONParser parser = new JSONParser();
        BulkRequest bulkRequest = new BulkRequest();
        Reader reader = new FileReader(file);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        JSONArray jsonArr = (JSONArray) jsonObject.get("extractGoodsTextDTOList");

        if (jsonArr.size() > 0) {
            for (int j = 0; j < jsonArr.size(); j++) {

                JSONObject jsonObj = (JSONObject) jsonArr.get(j);

                try {
                    XContentBuilder builder = XContentFactory.jsonBuilder();
                    builder.startObject();
                    {
                        builder.field("id", (Long) jsonObj.get("id"));
                        builder.field("keyword", (String) jsonObj.get("keyword"));
                        builder.field("name", (String) jsonObj.get("name"));
                        builder.field("brand", (String) jsonObj.get("brand"));
                        builder.field("price", (Long) jsonObj.get("price"));
                        builder.field("category", (String) jsonObj.get("category"));
                        builder.field("category1", (String) jsonObj.get("category1"));
                        builder.field("category2", (String) jsonObj.get("category2"));
                        builder.field("category3", (String) jsonObj.get("category3"));
                        builder.field("category4", (String) jsonObj.get("category4"));
                        builder.field("category5", (String) jsonObj.get("category5"));
                        builder.field("image", (String) jsonObj.get("image"));
                        builder.field("feature_vector", jsonObj.get("featureVector"));
                        builder.field("weight", (Double) jsonObj.get("weight"));
                        builder.field("popular", (Double) jsonObj.get("popular"));
                        builder.field("type", (String) jsonObj.get("type"));
                        builder.field("created_time", (String) jsonObj.get("createdTime"));
                        builder.field("updated_time", (String) jsonObj.get("updated_time"));
                    }
                    builder.endObject();
                    IndexRequest indexRequest = new IndexRequest(indexName)
                            .type("_doc")
                            .id(jsonObj.get("id").toString())
                            .source(builder);
                    bulkRequest.add(indexRequest);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

        return CompletableFuture.supplyAsync(() -> {
            return jsonArr.size();
        });
    }

}
