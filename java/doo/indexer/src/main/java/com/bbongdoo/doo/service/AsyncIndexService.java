package com.bbongdoo.doo.service;

import com.bbongdoo.doo.domain.GoodsText;
import com.bbongdoo.doo.domain.GoodsTextRepository;
import com.bbongdoo.doo.utils.ParseVector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncIndexService {
    private final GoodsTextRepository goodsTextRepository;
    private final GoodsAsyncService goodsAsyncService;
    private final IndicesService indicesService;
    private final SearchService searchService;

    private List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();
    private String ALIAS = "test_sync";


    public void staticIndex() {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
        String indexName = ALIAS + "-" + LocalDateTime.now().format(dateTimeFormatter).toString();
        String oldIndex = "";

        try {
            if (indicesService.existAlias(ALIAS)) {
                List<String> indexs = indicesService.getIndexToAlias(ALIAS);
                oldIndex = indexs.get(0);
            }
            if (indicesService.existIndex(indexName)) {
                List<String> indices = indicesService.getIndexToAlias(ALIAS);
                if (indices.size() == 0) {
                    indicesService.getIndexInfo(ALIAS + "*").stream().forEach(x -> {
                        try {
                            indicesService.deleteIndex(x);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    indicesService.createIndex(indexName);
                } else {
                    oldIndex = indices.get(0);
                }
            } else {
                indicesService.createIndex(indexName);
            }

            long count = goodsTextRepository.count();
            int chunk = 100;
            double endPage = Math.ceil(count / chunk);

            for (int i = 0; i < endPage + 1; i++) {
                CompletableFuture<Integer> completableFuture = goodsAsyncService.task(i, chunk, indexName);
                completableFutures.add(completableFuture);
            }

            for (CompletableFuture<Integer> future : completableFutures) {
                try {
                    future.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
            log.info("End info ");
            System.out.println(count);
            goodsAsyncService.indexAfterProcess(indexName, oldIndex);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void dynamicIndex() {


        try {

            LocalDateTime updatedTime = searchService.getUpdatedDate(ALIAS);


            List<GoodsText> goodsList = null;
            if (updatedTime == null) {
                goodsList = goodsTextRepository.findAll();
            } else {
                goodsList = goodsTextRepository.findAllByUpdatedTimeGreaterThan(updatedTime);
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

                searchService.processDynamic(bulkRequest, ALIAS);

            } else {
                System.out.println("end");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
