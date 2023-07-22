package com.bbongdoo.doo.service;

import com.bbongdoo.doo.model.response.CommonResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.Nested;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AggsService {

    private final ResponseService responseService;
    private final RestHighLevelClient client;
    private final String ALIAS = "aggs_doo";

    public CommonResult getProducts() {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(ALIAS);
        try {
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            QueryBuilder queryBuilder = QueryBuilders.boolQuery().must(
                    QueryBuilders.matchAllQuery()
            );

            searchSourceBuilder.query(queryBuilder);
            searchSourceBuilder.size(0);

            TermsAggregationBuilder mallType = new TermsAggregationBuilder("MALL_TYPE", null).field("mallType");
            searchSourceBuilder.aggregation(mallType);
            TermsAggregationBuilder benefit = new TermsAggregationBuilder("BENEFIT", null).field("benefit");
            searchSourceBuilder.aggregation(benefit);
            RangeAggregationBuilder grade = new RangeAggregationBuilder("GRADE").field("grade")
                    .addUnboundedTo(1)
                    .addRange(1, 2)
                    .addRange(2, 3)
                    .addRange(3, 4)
                    .addRange(4, 5)
                    .addUnboundedFrom(5);
            searchSourceBuilder.aggregation(grade);
            NestedAggregationBuilder reseller = new NestedAggregationBuilder("RESELLERS", "resellers");
                reseller.subAggregation(new TermsAggregationBuilder("RESELLERS_RESELLER", null).field("resellers.reseller"));
            searchSourceBuilder.aggregation(reseller);

            searchRequest.source(searchSourceBuilder);

            System.out.println(searchRequest.source());
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            RestStatus status = searchResponse.status();

            List<ReturnAggs> returnAggs = new ArrayList<>();
            if (status == RestStatus.OK) {
                Aggregations aggregations = searchResponse.getAggregations();

                Terms benefitAggs = aggregations.get("BENEFIT");
                for (Terms.Bucket bucketBenefit : benefitAggs.getBuckets()) {
                    returnAggs.add(new ReturnAggs(bucketBenefit.getKey().toString(), bucketBenefit.getDocCount()));
                }

                Terms mallTypeAggs = aggregations.get("MALL_TYPE");
                for (Terms.Bucket bucketMallType : mallTypeAggs.getBuckets()) {
                    returnAggs.add(new ReturnAggs(bucketMallType.getKey().toString(), bucketMallType.getDocCount()));
                }

                Range gradeAggs = aggregations.get("GRADE");
                for (Range.Bucket bucketGrade : gradeAggs.getBuckets()) {
                    returnAggs.add(new ReturnAggs(bucketGrade.getKey().toString(), bucketGrade.getDocCount()));
                }

                Nested resellersAggs=aggregations.get("RESELLERS");
                Terms resellerAggs=resellersAggs.getAggregations().get("RESELLERS_RESELLER");
                for (Terms.Bucket bucketReseller : resellerAggs.getBuckets()) {
                    returnAggs.add(new ReturnAggs(bucketReseller.getKey().toString(), bucketReseller.getDocCount()));
                }

            }

            return responseService.getListResult(returnAggs);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new CommonResult();
    }

}

@Getter
@Setter
@RequiredArgsConstructor
class ReturnAggs {
    private final String key;
    private final long count;
}