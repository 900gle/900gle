package com.bbongdoo.doo.service;


import com.bbongdoo.doo.apis.IndexApi;
import com.bbongdoo.doo.apis.IndexGoodsApi;
import com.bbongdoo.doo.domain.Goods;
import com.bbongdoo.doo.domain.GoodsRepository;
import com.bbongdoo.doo.domain.Products;
import com.bbongdoo.doo.domain.ProductsRepository;
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

    private final GoodsRepository goodsRepository;

    private final RestHighLevelClient client;

    private LocalDateTime updatedTime;

    private String PREFIX = "goods";

    public void staticIndex() {

        String indexName = PREFIX+"-" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString();

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

            List<Goods> goodsList = goodsRepository.findAll();
            BulkRequest bulkRequest = new BulkRequest();
            goodsList.forEach(
                    x -> {
                        try {
                            XContentBuilder builder = XContentFactory.jsonBuilder();
                            builder.startObject();
                            {

                                builder.field("id", x.getId());
                                builder.field("keyword", x.getKeyword());
                                builder.field("name", x.getName());
                                builder.field("brand", x.getBrand());
                                builder.field("price", x.getPrice());
                                builder.field("category", x.getCategory());
                                builder.field("category1", x.getCategory1());
                                builder.field("category2", x.getCategory2());
                                builder.field("category3", x.getCategory3());
                                builder.field("category4", x.getCategory4());
                                builder.field("category5", x.getCategory5());
                                builder.field("image", x.getImage());
                                builder.field("feature_vector", ParseVector.parse(x.getFeatureVector()));
                                builder.field("weight", x.getWeight());
                                builder.field("popular", x.getPopular());
                                builder.field("type", x.getType());
                                builder.field("created_time", x.getCreatedTime());
                                builder.field("updated_time", x.getUpdatedTime());
                            }
                            builder.endObject();
                            IndexRequest indexRequest = new IndexRequest(indexName)
                                    .type("_doc")
                                    .id(x.getId().toString())
                                    .source(builder);
                            bulkRequest.add(indexRequest);

                        } catch (IOException e) {
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

            System.out.println("old name : " + oldIndexName);
            System.out.println("index name : " + indexName);

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


    public void dynamicIndex() {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(PREFIX);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

//        boolQueryBuilder.must(QueryBuilders.termQuery("name", searchWord));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("name", searchWord));


        searchSourceBuilder.query(boolQueryBuilder);

        searchSourceBuilder.size(1);
        searchSourceBuilder.sort("updated_time", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);


        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            List<Map<String, Object>> returnValue = new ArrayList<>();
            SearchHit[] results = searchResponse.getHits().getHits();


            Arrays.stream(results).forEach(hit -> {
                Map<String, Object> result = hit.getSourceAsMap();


//
//                System.out.println(result.get("name"));
//                System.out.println(result.get("id"));
                System.out.println(result.get("updated_time"));

                updatedTime = LocalDateTime.parse(result.get("updated_time").toString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);


            });


            List<Goods> goodsList = null;

            if (updatedTime == null) {


                goodsList = goodsRepository.findAll();


            } else {
                goodsList = goodsRepository.findAllByUpdatedTimeGreaterThan(updatedTime);

            }


            if (goodsList.size() > 0) {
                BulkRequest bulkRequest = new BulkRequest();
                goodsList.forEach(
                        x -> {
                            try {
                                XContentBuilder builder = XContentFactory.jsonBuilder();
                                builder.startObject();
                                {

                                    builder.field("id", x.getId());
                                    builder.field("keyword", x.getKeyword());
                                    builder.field("name", x.getName());
                                    builder.field("brand", x.getBrand());
                                    builder.field("price", x.getPrice());
                                    builder.field("category", x.getCategory());
                                    builder.field("category1", x.getCategory1());
                                    builder.field("category2", x.getCategory2());
                                    builder.field("category3", x.getCategory3());
                                    builder.field("category4", x.getCategory4());
                                    builder.field("category5", x.getCategory5());
                                    builder.field("image", x.getImage());
                                    builder.field("feature_vector", ParseVector.parse(x.getFeatureVector()));
                                    builder.field("weight", x.getWeight());
                                    builder.field("popular", x.getPopular());
                                    builder.field("type", x.getType());
                                    builder.field("created_time", x.getCreatedTime());
                                    builder.field("updated_time", x.getUpdatedTime());
                                }
                                builder.endObject();
                                IndexRequest indexRequest = new IndexRequest("goods")
                                        .type("_doc")
                                        .id(x.getId().toString())
                                        .source(builder);
                                bulkRequest.add(indexRequest);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );

                BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);


                System.out.println(bulkResponse.buildFailureMessage());

                FlushRequest flushRequest = new FlushRequest(PREFIX);
                FlushResponse flushResponse = client.indices().flush(flushRequest, RequestOptions.DEFAULT);
                ForceMergeRequest forceMergeRequest = new ForceMergeRequest(PREFIX);
                ForceMergeResponse forceMergeResponse = client.indices().forcemerge(forceMergeRequest, RequestOptions.DEFAULT);
                IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();


//                returnValue.add(result);


            } else {

                System.out.println("end");

            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
