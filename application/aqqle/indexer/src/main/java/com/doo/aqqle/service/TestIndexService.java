package com.doo.aqqle.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestIndexService {
   private final RestHighLevelClient high;
   private final ElasticsearchClient low;


   public void index(){
        try {
//
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("high-level");
            high.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);

//           low.indices().create(c -> c.index("low-level"));

            String setting = getResourceData("setting.json");
            String mapping = getResourceData("mapping.json");

            CreateIndexRequest request = new CreateIndexRequest("high-level");
            request.settings(setting, XContentType.JSON);
            request.mapping(mapping, XContentType.JSON);

            CreateIndexResponse createIndexResponse = high.indices().create(request, RequestOptions.DEFAULT);
            log.info("response : {}", createIndexResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }
   }

    private String getResourceData(String name) throws IOException {
        Resource resource = new ClassPathResource(name);
        InputStream inputStream = resource.getInputStream();
        byte[] byteData = FileCopyUtils.copyToByteArray(inputStream);
        return new String(byteData, StandardCharsets.UTF_8);
    }

}
