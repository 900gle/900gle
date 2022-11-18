package com.bbongdoo.doo.service;


import com.bbongdoo.doo.apis.IndexGoodsApi;
import com.bbongdoo.doo.domain.Goods;
import com.bbongdoo.doo.domain.GoodsRepository;
import com.bbongdoo.doo.utils.ParseVector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsService {


    private final RestHighLevelClient client;

    private LocalDateTime updatedTime;

    private String PREFIX = "goods-kafka";

    public void staticIndex(List<String> data) {

        String indexName = PREFIX + "-" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString();

        try {

            GetIndexRequest requestGetIndex = new GetIndexRequest(indexName);
            boolean existsIndex = client.indices().exists(requestGetIndex, RequestOptions.DEFAULT);

            GetAliasesRequest aliasesRequest = new GetAliasesRequest(PREFIX);
            GetAliasesResponse getAliasesResponse = client.indices().getAlias(aliasesRequest, RequestOptions.DEFAULT);

            String oldIndexName = "";
            if (getAliasesResponse.getAliases().size() > 0) {
                oldIndexName = Optional.ofNullable(getAliasesResponse.getAliases().keySet().iterator().next()).orElse("");
            }

            if (existsIndex == false) {
                CreateIndexRequest request = IndexGoodsApi.createIndex(indexName);
                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            } else {

                if (!oldIndexName.equals(indexName)) {
                    DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
                    AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                    CreateIndexRequest request = IndexGoodsApi.createIndex(indexName);
                    CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
                }
            }

            BulkRequest bulkRequest = new BulkRequest();
            data.forEach(
                    x -> {
                        try {

                            JSONParser jsonParser = new JSONParser();
                            Object obj = jsonParser.parse(x);
                            JSONObject jsonObj = (JSONObject) obj;

                            XContentBuilder builder = XContentFactory.jsonBuilder();
                            builder.startObject();
                            {
                                builder.field("id", jsonObj.get("id"));
                                builder.field("keyword", jsonObj.get("keyword"));
                                builder.field("name", jsonObj.get("name"));
                                builder.field("brand", jsonObj.get("brand"));
                                builder.field("price", jsonObj.get("price"));
                                builder.field("category1", jsonObj.get("category1"));
                                builder.field("category2", jsonObj.get("category2"));
                                builder.field("category3", jsonObj.get("category3"));
                                builder.field("category4", jsonObj.get("category4"));
                                builder.field("category5", jsonObj.get("category5"));
                                builder.field("image", jsonObj.get("image"));
//                                builder.field("feature_vector", ParseVector.parse(jsonObj.get("keyword")));
                                builder.field("weight", jsonObj.get("weight"));
                                builder.field("popular", jsonObj.get("popular"));
                                builder.field("type", jsonObj.get("type"));
                                builder.field("created_time", jsonObj.get("created_time"));
                                builder.field("updated_time", jsonObj.get("updated_time"));
                            }
                            builder.endObject();
                            IndexRequest indexRequest = new IndexRequest(indexName)
                                    .type("_doc")
                                    .source(builder);
                            bulkRequest.add(indexRequest);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
            );

            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

            System.out.println(bulkResponse.buildFailureMessage());

            FlushRequest flushRequest = new FlushRequest(indexName);
            FlushResponse flushResponse = client.indices().flush(flushRequest, RequestOptions.DEFAULT);
            ForceMergeRequest forceMergeRequest = new ForceMergeRequest(indexName);
            ForceMergeResponse forceMergeResponse = client.indices().forcemerge(forceMergeRequest, RequestOptions.DEFAULT);
            IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();

            if (!StringUtils.isEmpty(oldIndexName) && !indexName.equals(oldIndexName)) {

                IndicesAliasesRequest.AliasActions aliasActionsAdd = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                        .index(indexName)
                        .alias(PREFIX);
                indicesAliasesRequest.addAliasAction(aliasActionsAdd);

                IndicesAliasesRequest.AliasActions aliasActionsRemove = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE)
                        .index(oldIndexName)
                        .alias(PREFIX);
                indicesAliasesRequest.addAliasAction(aliasActionsRemove);

            } else {


                IndicesAliasesRequest.AliasActions aliasActionsAdd = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                        .index(indexName)
                        .alias(PREFIX);

                indicesAliasesRequest.addAliasAction(aliasActionsAdd);


            }

            AcknowledgedResponse indicesAliasesResponse =
                    client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
