package com.doo.aqqle.service;

import com.doo.aqqle.annotation.IndexerLog;
import com.doo.aqqle.enums.ElasticStatic;
import com.doo.aqqle.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.xcontent.XContentType;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvestmentService {
    private final RestHighLevelClient client;

    private final GoodsRepository goodsRepository;

    private final InvestmentAsyncTaskService asyncTaskService;

    private List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();

    @IndexerLog
    public void index(String type) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String indexName = ElasticStatic.INVESTMENT.getAlias() + "-" + LocalDateTime.now().format(dateTimeFormatter).toString();

        try {
//            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
//            client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);

            String setting = getResourceData("investment_setting.json");
            String mapping = getResourceData("investment_mapping.json");

            CreateIndexRequest request = new CreateIndexRequest(indexName);
            request.settings(setting, XContentType.JSON);
            request.mapping(mapping, XContentType.JSON);

            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);

            String path = "/data/"+type+"/static/";
            File file = new File(path);

            String dir = Arrays.stream(file.list()).sorted().findFirst().get();

            System.out.println(dir);

            File files = new File(path + dir);
            List<String> fileList = Arrays.stream(files.list()).collect(Collectors.toList());

            for (int i = 0; i < fileList.size(); i++) {
                CompletableFuture<Integer> completableFuture = asyncTaskService.task(indexName, path + dir + "/" + fileList.get(i));
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

            FlushRequest flushRequest = new FlushRequest(indexName);
            FlushResponse flushResponse = client.indices().flush(flushRequest, RequestOptions.DEFAULT);
            ForceMergeRequest forceMergeRequest = new ForceMergeRequest(indexName);
            ForceMergeResponse forceMergeResponse = client.indices().forcemerge(forceMergeRequest, RequestOptions.DEFAULT);
            IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();

            IndicesAliasesRequest.AliasActions aliasActionsAdd = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                    .index(indexName)
                    .alias(ElasticStatic.INVESTMENT.getAlias());

            indicesAliasesRequest.addAliasAction(aliasActionsAdd);

            AcknowledgedResponse indicesAliasesResponse =
                    client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
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
