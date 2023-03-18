package com.bbongdoo.doo.service;

import com.bbongdoo.doo.apis.IndexApi;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final RestHighLevelClient client;

    public LocalDateTime updatedTime;

    public LocalDateTime getUpdatedDate(String alias) throws IOException{


        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(alias);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        boolQueryBuilder.must(QueryBuilders.termQuery("name", searchWord));
//        boolQueryBuilder.should(QueryBuilders.matchQuery("name", searchWord));
        searchSourceBuilder.query(boolQueryBuilder);

        searchSourceBuilder.size(1);
        searchSourceBuilder.sort("updated_time", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        List<Map<String, Object>> returnValue = new ArrayList<>();
        SearchHit[] results = searchResponse.getHits().getHits();

        Arrays.stream(results).forEach(hit -> {
            Map<String, Object> result = hit.getSourceAsMap();
            updatedTime = LocalDateTime.parse(result.get("updated_time").toString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        });

        return updatedTime;

    }


    public void processDynamic(BulkRequest bulkRequest, String alias) throws IOException{
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

        FlushRequest flushRequest = new FlushRequest(alias);
        FlushResponse flushResponse = client.indices().flush(flushRequest, RequestOptions.DEFAULT);
        ForceMergeRequest forceMergeRequest = new ForceMergeRequest(alias);
        ForceMergeResponse forceMergeResponse = client.indices().forcemerge(forceMergeRequest, RequestOptions.DEFAULT);
        IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
    }


}
