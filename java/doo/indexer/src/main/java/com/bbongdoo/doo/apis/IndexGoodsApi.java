package com.bbongdoo.doo.apis;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

public class IndexGoodsApi {

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
                .startObject("tokenizer")
                    .startObject("doo-nori-tokenizer")
                        .field("type", "nori_tokenizer")
                        .field("decompound_mode", "mixed")
                        .field("user_dictionary", "user_dictionary.txt")
                    .endObject()
                .endObject()
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
                    .startObject("doo-nori-analyzer")
                        .field("type", "custom")
                        .field("tokenizer", "doo-nori-tokenizer")
                            .array("filter", new String[]{
                                            "doo-nori-posfilter",
                                            "nori_readingform",
                                            "doo-synonym-filter",
                                            "doo-stop-filter"
                                    }
                            )
                        .endObject()
                    .endObject()
                        .startObject("filter")
                            .startObject("doo-nori-posfilter")
                                .field("type", "nori_part_of_speech")
                                    .array("stoptaags", new String[]{
                                                    "E", "IC", "J", "MAG", "MM", "NA", "NR", "SC",
                                                    "SE", "SF", "SH", "SL", "SN", "SP", "SSC", "SSO",
                                                    "SY", "UNA", "UNKNOWN", "VA", "VCN", "VCP", "VSV",
                                                    "VV", "VX", "XPN", "XR", "XSA", "XSN", "XSV"
                                            }
                                    )
                            .endObject()
                            .startObject("doo-synonym-filter")
                                .field("type", "synonym")
                                .field("synonyms_path", "synonymsFilter.txt")
                            .endObject()
                            .startObject("doo-stop-filter")
                                .field("type", "stop")
                                .field("stopwords_path", "stopFilter.txt")
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
                builder.startObject("id");
                {
                    builder.field("type", "long");
                }
                builder.endObject();

                builder.startObject("name");
                {
                    builder.field("type", "text");
                    builder.startObject("fields");
                        builder.startObject("nori");
                                builder.field("type", "text");
                                builder.field("analyzer", "doo-nori-analyzer");
                            builder.endObject();
                            builder.startObject("jamo");
                                builder.field("type", "text");
                                builder.field("analyzer", "jamo-analyzer");
                            builder.endObject();
                            builder.startObject("chosung");
                                builder.field("type", "text");
                                builder.field("analyzer", "chosung-analyzer");
                            builder.endObject();
                            builder.startObject("keyword");
                                builder.field("type", "keyword");
                            builder.endObject();
                    builder.endObject();
                }
                builder.endObject();

                builder.startObject("category");
                {
                    builder.field("type", "text");
                    builder.startObject("fields");
                        builder.startObject("keyword");
                            builder.field("type", "keyword");
                            builder.endObject();
                        builder.endObject();
                }
                builder.endObject();

                builder.startObject("category1");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("category2");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("category3");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("category4");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("category5");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("price");
                {
                    builder.field("type", "long");
                }
                builder.endObject();

                builder.startObject("brand");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("keyword");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("image");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
                builder.startObject("popular");
                {
                    builder.field("type", "integer");
                    builder.field("null_value", "1");
                }
                builder.endObject();
                builder.startObject("weight");
                {
                    builder.field("type", "float");
                }
                builder.endObject();
                builder.startObject("feature_vector");
                {
                    builder.field("type", "dense_vector");
                    builder.field("dims", 512);
                }
                builder.endObject();
                builder.startObject("type");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();

                builder.startObject("created_time");
                {
                    builder.field("type", "date");
                    builder.field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||strict_date_hour_minute_second_millis||strict_date_optional_time");
                }
                builder.endObject();
                builder.startObject("updated_time");
                {
                    builder.field("type", "date");
                    builder.field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||strict_date_hour_minute_second_millis||strict_date_optional_time");
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