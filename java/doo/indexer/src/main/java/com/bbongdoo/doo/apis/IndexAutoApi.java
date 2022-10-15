package com.bbongdoo.doo.apis;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class IndexAutoApi {

    public static CreateIndexRequest createIndex(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 0)
        );

        XContentBuilder settingBuilder = XContentFactory.jsonBuilder()
                .startObject()
                    .field("number_of_shards", 3)
                    .field("number_of_replicas", 0)
                    .startObject("analysis")
                        .startObject("analyzer")
                            .startObject("jamo-analyzer")
                                .field("type", "custom")
                                .field("tokenizer", "standard")
                                .array("filter", new String[]{
                                                "doo-jamo"
                                        }
                                )
                            .endObject()

                            .startObject("chosung-analyzer")
                                .field("type", "custom")
                                .field("tokenizer", "standard")
                                .array("filter", new String[]{
                                                "doo-chosung"
                                        }
                                )
                            .endObject()

                            .startObject("eng2kor-analyzer")
                                .field("type", "custom")
                                .field("tokenizer", "standard")
                                .array("filter", new String[]{
                                                "doo-eng2kor"
                                        }
                                )
                            .endObject()

                            .startObject("kor2eng-analyzer")
                                .field("type", "custom")
                                .field("tokenizer", "standard")
                                .array("filter", new String[]{
                                                "doo-kor2eng"
                                        }
                                )
                            .endObject()

                            .startObject("spell-analyzer")
                                .field("type", "custom")
                                .field("tokenizer", "standard")
                                .array("filter", new String[]{
                                                "doo-spell"
                                        }
                                )
                            .endObject()
                        .endObject()
                    .endObject()
                .endObject();
        request.settings(settingBuilder);

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("name");
                {
                    builder.field("type", "text");
                    builder.startObject("fields");
                        builder.startObject("jamo");
                            builder.field("type", "text");
                            builder.field("analyzer", "jamo-analyzer");
                        builder.endObject();
                        builder.startObject("chosung");
                            builder.field("type", "text");
                            builder.field("analyzer", "chosung-analyzer");
                        builder.endObject();
                        builder.startObject("eng2kor");
                            builder.field("type", "text");
                            builder.field("analyzer", "eng2kor-analyzer");
                        builder.endObject();
                        builder.startObject("kor2eng");
                            builder.field("type", "text");
                            builder.field("analyzer", "kor2eng-analyzer");
                        builder.endObject();
                        builder.startObject("spell");
                            builder.field("type", "text");
                            builder.field("analyzer", "spell-analyzer");
                        builder.endObject();
                        builder.startObject("keyword");
                            builder.field("type", "keyword");
                        builder.endObject();
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();

        request.mapping(builder);
        return request;

    }
}