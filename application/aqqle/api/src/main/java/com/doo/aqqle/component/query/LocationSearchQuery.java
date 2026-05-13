package com.doo.aqqle.component.query;

import com.doo.aqqle.model.request.LocationRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Component;

@Component
public class LocationSearchQuery {


    public QueryBuilder getDistanceBuilder(LocationRequest request) {
        double lat =  37.48822057363974; //위도
        double lon =  126.90500268692999; //경도

        return QueryBuilders.boolQuery()
                .must(QueryBuilders.geoDistanceQuery("location").point(lat, lon).distance( request.getDistance() +"km"))
                .filter(QueryBuilders.termQuery("country_code", request.getCountryCode()));
    }




}
