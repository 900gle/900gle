package com.doo.aqqle.service;


import com.doo.aqqle.repository.InvestmentsRepository;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestmentAsyncTaskService {

    private final RestHighLevelClient client;


    private final InvestmentsRepository investmentsRepository;

    @Async("executor")
    public CompletableFuture<Integer> task(String indexName, String file) throws FileNotFoundException, ParseException, IOException {

        JSONParser parser = new JSONParser();
        BulkRequest bulkRequest = new BulkRequest();
        Reader reader = new FileReader(file);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        JSONArray jsonArr = (JSONArray) jsonObject.get("investmentsDTOList");

        if (jsonArr.size() > 0) {
            for (int j = 0; j < jsonArr.size(); j++) {

                JSONObject jsonObj = (JSONObject) jsonArr.get(j);

                try {
                    XContentBuilder builder = XContentFactory.jsonBuilder();
                    builder.startObject();
                    {
                        builder.field("id", (Long) jsonObj.get("id"));
                        builder.field("company", (String) jsonObj.get("company"));
                        builder.field("exchange", (String) jsonObj.get("exchange"));
                        builder.field("type", (String) jsonObj.get("type"));
                        builder.field("price", (Long) jsonObj.get("price"));
                        builder.field("quantity", (Long) jsonObj.get("quantity"));
                        builder.field("useYn", (String) jsonObj.get("useYn"));
                        builder.field("createdTime", (String) jsonObj.get("createdTime"));
                        builder.field("updatedTime", (String) jsonObj.get("updatedTime"));
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
