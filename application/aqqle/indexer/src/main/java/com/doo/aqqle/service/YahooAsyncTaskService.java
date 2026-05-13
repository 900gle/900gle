package com.doo.aqqle.service;


import com.doo.aqqle.repository.InvestmentsRepository;
import com.doo.aqqle.repository.StockDataRepository;
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
public class YahooAsyncTaskService {

    private final RestHighLevelClient client;


    private final StockDataRepository stockDataRepository;

    @Async("executor")
    public CompletableFuture<Integer> task(String indexName, String file) throws FileNotFoundException, ParseException, IOException {

        JSONParser parser = new JSONParser();
        BulkRequest bulkRequest = new BulkRequest();
        Reader reader = new FileReader(file);
        JSONObject jsonObject = (JSONObject) parser.parse(reader);

        JSONArray jsonArr = (JSONArray) jsonObject.get("stockDataDTOList");

        if (jsonArr.size() > 0) {
            for (int j = 0; j < jsonArr.size(); j++) {

                JSONObject jsonObj = (JSONObject) jsonArr.get(j);

                try {
                    XContentBuilder builder = XContentFactory.jsonBuilder();
                    builder.startObject();
                    {
                        builder.field("id", (Long) jsonObj.get("id"));
                        builder.field("company", (String) jsonObj.get("company"));
                        builder.field("companyCode", (String) jsonObj.get("companyCode"));
                        builder.field("tradingDate", (String) jsonObj.get("tradingDate"));
                        builder.field("open", (Double) jsonObj.get("open"));
                        builder.field("high", (Double) jsonObj.get("high"));
                        builder.field("low", (Double) jsonObj.get("low"));
                        builder.field("close", (Double) jsonObj.get("close"));
                        builder.field("adjClose", (Double) jsonObj.get("adjClose"));
                        builder.field("volume", (Long) jsonObj.get("volume"));
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
