package com.bbongdoo.doo.service;

import com.bbongdoo.doo.apis.IndexApi;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndicesService {

    private final RestHighLevelClient client;

    public List<String> getIndexToAlias (String alias) throws IOException {
        GetAliasesRequest request = new GetAliasesRequest();
        request.aliases(alias);
        RequestOptions requestOptions = RequestOptions.DEFAULT;
        GetAliasesResponse response = client.indices().getAlias(request, requestOptions);
       return response.getAliases().keySet().stream().collect(Collectors.toList());
    }

    public List<String> getIndexInfo (String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        RequestOptions requestOptions = RequestOptions.DEFAULT;
        GetIndexResponse getIndexResponse = client.indices().get(request, requestOptions);
        return Arrays.stream(getIndexResponse.getIndices()).collect(Collectors.toList());
    }

    public boolean deleteIndex (String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        RequestOptions requestOptions = RequestOptions.DEFAULT;
        AcknowledgedResponse deleteIndexResponse = client.indices().delete(request, requestOptions);
        return deleteIndexResponse.isAcknowledged();
    }

    public boolean createIndex(String indexName) throws IOException {
        CreateIndexRequest request = IndexApi.createIndex(indexName);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        return true;
    }

    public boolean existIndex(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        request.local(false);
        request.humanReadable(true);
        request.includeDefaults(false);
        request.indicesOptions(IndicesOptions.strictExpandOpen());
        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    public boolean existAlias(String alias) throws IOException {
        GetAliasesRequest request = new GetAliasesRequest();
        request.aliases(alias);
        return client.indices().existsAlias(request, RequestOptions.DEFAULT);

    }

}
