package com.bbongdoo.doo.service;



import com.bbongdoo.doo.config.Client;
import com.bbongdoo.doo.entities.Shop;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DocumentBulkService {



    public void postBulkDocument(Shop shop) {
//        Random rnd = new Random();
//        String randomStr = String.valueOf((char) ((int) (rnd.nextInt(26)) + 65));





        String indexName = "shop-" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString();

        BulkRequest bulkRequest = new BulkRequest();

        try {
            RestHighLevelClient client = new Client().getClient();


            System.out.println("-----------------------------------------------------------");


            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("title", shop.getTitle());
                builder.field("brand", shop.getBrand());
                builder.field("price", shop.getPrice());
                builder.field("category", shop.getCategory());

            }
            builder.endObject();

            IndexRequest indexRequest = new IndexRequest(indexName)
                    .type("_doc")
                    .id(null)
                    .source(builder);


            bulkRequest.add(indexRequest);
            BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

            FlushRequest request = new FlushRequest(indexName);
            FlushResponse flushResponse = client.indices().flush(request, RequestOptions.DEFAULT);

            ForceMergeRequest forceMergeRequest = new ForceMergeRequest(indexName);
            ForceMergeResponse forceMergeResponse = client.indices().forcemerge(forceMergeRequest, RequestOptions.DEFAULT);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
