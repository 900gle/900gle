package com.bbongdoo.doo.service;


import com.bbongdoo.doo.apis.IndexApi;
import com.bbongdoo.doo.apis.IndexLocationApi;
import com.bbongdoo.doo.config.Client;
import com.bbongdoo.doo.domain.Locations;
import com.bbongdoo.doo.domain.LocationsRepository;
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
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class LocationService {

    @Autowired
    LocationsRepository locationsRepository;

    public void staticIndex() {

        String indexName = "location-" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString();
        try {
            RestHighLevelClient client = new Client().getClient();

            GetIndexRequest requestGetIndex = new GetIndexRequest(indexName);
            boolean existsIndex = client.indices().exists(requestGetIndex, RequestOptions.DEFAULT);

            String oldIndexName = "";
            if (existsIndex == false) {
                CreateIndexRequest request = IndexLocationApi.createIndex(indexName);
                CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            } else {

                GetAliasesRequest aliasesRequest = new GetAliasesRequest("location");
                GetAliasesResponse getAliasesResponse = client.indices().getAlias(aliasesRequest, RequestOptions.DEFAULT);
                oldIndexName = getAliasesResponse.getAliases().keySet().iterator().next();

                if (!oldIndexName.equals(indexName)) {
                    DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
                    AcknowledgedResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
                    CreateIndexRequest request = IndexApi.createIndex(indexName);
                    CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
                }
            }
            List<Locations> locations = locationsRepository.findAll();

            BulkRequest bulkRequest = new BulkRequest();
            locations.forEach(
                    x -> {
                        try {
                            XContentBuilder builder = XContentFactory.jsonBuilder();
                            builder.startObject();
                            {
                                builder.field("id", x.getId());
                                builder.field("ip1", x.getIp1());
                                builder.field("ip2", x.getIp2());
                                builder.field("country", x.getCountry());
                                builder.field("city", x.getCity());
                                builder.field("town", x.getTown());
//                                builder.field("location.lat", x.getLat());
//                                builder.field("location.lon", x.getLon());
//                                builder.field("location", new GeoPoint(x.getLat(), x.getLon()) );
                                builder.field("zone", x.getZone());
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

            FlushRequest flushRequest = new FlushRequest(indexName);
            FlushResponse flushResponse = client.indices().flush(flushRequest, RequestOptions.DEFAULT);

            ForceMergeRequest forceMergeRequest = new ForceMergeRequest(indexName);
            ForceMergeResponse forceMergeResponse = client.indices().forcemerge(forceMergeRequest, RequestOptions.DEFAULT);

            IndicesAliasesRequest indicesAliasesRequest = new IndicesAliasesRequest();
            if (!StringUtils.isEmpty(oldIndexName) && !indexName.equals(oldIndexName)) {

                IndicesAliasesRequest.AliasActions aliasActionsAdd = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                        .index(indexName)
                        .alias("location");
                indicesAliasesRequest.addAliasAction(aliasActionsAdd);

                IndicesAliasesRequest.AliasActions aliasActionsRemove = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.REMOVE)
                        .index(oldIndexName)
                        .alias("location");
                indicesAliasesRequest.addAliasAction(aliasActionsRemove);

            } else {

                IndicesAliasesRequest.AliasActions aliasActionsAdd = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD)
                        .index(indexName)
                        .alias("location");
                indicesAliasesRequest.addAliasAction(aliasActionsAdd);

            }

            AcknowledgedResponse indicesAliasesResponse =
                    client.indices().updateAliases(indicesAliasesRequest, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
