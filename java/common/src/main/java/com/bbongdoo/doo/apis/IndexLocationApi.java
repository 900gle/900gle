package com.bbongdoo.doo.apis;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class IndexLocationApi {

    public static CreateIndexRequest createIndex(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 0)
        );

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("ip1");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("ip2");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("country");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
                builder.startObject("city");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("town");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("lat");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("lon");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("location");
                {
                    builder.field("type", "geo_point");
                }
                builder.endObject();
                builder.startObject("zone");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
                builder.startObject("created_time");
                {
                    builder.field("type", "date");
                }
                builder.endObject();
                builder.startObject("updated_time");
                {
                    builder.field("type", "date");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping(builder);
//        request.alias(
//                new Alias("shop")
//        );
        return request;
    }
}
