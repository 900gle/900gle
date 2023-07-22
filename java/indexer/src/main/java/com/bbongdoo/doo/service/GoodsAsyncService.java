package com.bbongdoo.doo.service;

import com.bbongdoo.doo.domain.GoodsRepository;
import com.bbongdoo.doo.domain.GoodsText;
import com.bbongdoo.doo.domain.GoodsTextRepository;
import com.bbongdoo.doo.utils.ParseVector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


@Slf4j
@Service
@RequiredArgsConstructor
public class GoodsAsyncService {

    private final GoodsTextRepository goodsTextRepository;

    private final GoodsRepository goodsRepository;

    private final RestHighLevelClient client;

    private LocalDateTime updatedTime;

    private String PREFIX = "goods";


    @Async("executor")
    public CompletableFuture<Integer> task(int i, int chunk, String indexName) {
        PageRequest pageRequest = PageRequest.of(i, chunk);
        Page<GoodsText> goodsTexts = goodsTextRepository.findAllByOrderByIdAsc(pageRequest);


        List<GoodsText> goodsTextList = new ArrayList<>();

        StringBuilder stringBuilder = new StringBuilder();

        BulkRequest bulkRequest = new BulkRequest();


        try {
            goodsTexts.stream().forEach(
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
//
            System.out.println(bulkResponse.buildFailureMessage());
//


        } catch (IOException e) {
            e.printStackTrace();
        }

        return CompletableFuture.supplyAsync(goodsTexts::getSize);
    }


    public void indexAfterProcess(String indexName, String oldIndexName) throws IOException {

        FlushRequest flushRequest = new FlushRequest(indexName);
        FlushResponse flushResponse = client.indices().flush(flushRequest, RequestOptions.DEFAULT);
        ForceMergeRequest forceMergeRequest = new ForceMergeRequest(indexName);
        ForceMergeResponse forceMergeResponse = client.indices().forcemerge(forceMergeRequest, RequestOptions.DEFAULT);

        IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();

        if (StringUtils.isEmpty(oldIndexName)) {

            System.out.println("oldIndexName 1 : " +oldIndexName);

            IndicesAliasesRequest.AliasActions addIndexAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                    .index(indexName)
                    .alias(PREFIX);
            indicesAliasesRequest.addAliasAction(addIndexAction);
            AcknowledgedResponse indicesAliasesResponse =
                    client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);

        } else {

            System.out.println("indexName : "+ indexName);
            System.out.println("oldIndexName 2: " +oldIndexName);

            IndicesAliasesRequest.AliasActions addIndexAction = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                    .indices(indexName)
                    .alias(PREFIX);

            indicesAliasesRequest.addAliasAction(addIndexAction);
                    client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);

            indicesAliasesRequest = new IndicesAliasesRequest();

            IndicesAliasesRequest.AliasActions removeAction =
                    new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE)
                            .index(oldIndexName)
                            .alias(PREFIX);
            indicesAliasesRequest.addAliasAction(removeAction);
                    client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);



            indicesAliasesRequest = new IndicesAliasesRequest();

            IndicesAliasesRequest.AliasActions removeIndexAction =
                    new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE_INDEX)
                            .index(oldIndexName);

            indicesAliasesRequest.addAliasAction(removeIndexAction);
            client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);

        }

//        AcknowledgedResponse indicesAliasesResponse =
//                client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);

    }

}



