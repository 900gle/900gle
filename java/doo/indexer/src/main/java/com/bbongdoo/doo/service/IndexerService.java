package com.bbongdoo.doo.service;

import com.bbongdoo.doo.apis.IndexApi;
import com.bbongdoo.doo.config.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IndexerService {



    public static void main(String[] args) {

        String indexName = "shop-" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString();

        try {
            RestHighLevelClient client = new Client().getClient();

            CreateIndexRequest request = IndexApi.createIndex(indexName);
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
