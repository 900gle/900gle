package com.doo.aqqle.component.query;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScriptScoreFunctionBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ShopSearchQuery {


    public QueryBuilder getShopQueryBuilder(String searchWord) {
        return QueryBuilders.boolQuery().must(
                QueryBuilders.multiMatchQuery(searchWord, "name", "category")
        ).should(
                QueryBuilders.multiMatchQuery(searchWord, "category1", "category2", "category3", "category4", "category5")
        );
    }

    public FunctionScoreQueryBuilder.FilterFunctionBuilder[] getShopFunctionScoreQueryBuilder(Map<String, Object> map) {
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] functionScoreQueryBuilder  = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                new ScriptScoreFunctionBuilder(
                                        new Script(
                                                ScriptType.INLINE,
                                                Script.DEFAULT_SCRIPT_LANG,
                                                "cosineSimilarity(params.query_vector, 'feature_vector') + 1.0",
                                                map)
                                ).setWeight(0.1f)
                        )
                };

        return functionScoreQueryBuilder;
    }

    public FunctionScoreQueryBuilder.FilterFunctionBuilder[] getShopFunctionScoreQueryBuilder(Script script) {
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] functionScoreQueryBuilder  = new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        new ScriptScoreFunctionBuilder(script).setWeight(0.1f)
                )
        };
        return functionScoreQueryBuilder;
    }






}
