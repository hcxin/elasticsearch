package com.chen.elasticsearch.service.impl;

import com.chen.elasticsearch.query.CatQuery;
import com.chen.elasticsearch.service.ElasticService;
import com.chen.elasticsearch.util.ReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

/**
 * ElasticServiceImpl。
 *
 * @author xinhaichen
 */
@Service
public class ElasticServiceImpl implements ElasticService {


    private static final String CAT_INDEX_NAME = "cat";

    private static final String CREATE_TIME = "createTime";

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Override
    public void getCatList(CatQuery catQuery) {
        SearchResponse searchResponse = searchCatList(catQuery);
        if (Objects.nonNull(searchResponse)) {
            SearchHits searchHits = searchResponse.getHits();
            SearchHit[] searchHitList = searchHits.getHits();
            System.out.println(searchHits.totalHits);

            for (SearchHit searchHit : searchHitList) {
            }
        }

    }

    /**
     * 查询。
     *
     * @param catQuery 参数
     * @return 搜索结果
     */
    public SearchResponse searchCatList(CatQuery catQuery) {
        SearchSourceBuilder sourceBuilder = buildSearchSourceBuilder(catQuery);
        sourceBuilder.from((catQuery.getPageNum() - 1) * catQuery.getPageSize());
        sourceBuilder.size(catQuery.getPageSize());
        SearchRequest searchRequest = new SearchRequest(CAT_INDEX_NAME);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = null;
            try {
                searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
        }
        return searchResponse;
    }

    private SearchSourceBuilder buildSearchSourceBuilder(CatQuery catQuery){
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
        sourceBuilder.from((catQuery.getPageNum() - 1) * catQuery.getPageSize());
        sourceBuilder.size(catQuery.getPageSize());
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //法人组织标识
        if (StringUtils.isNotBlank(catQuery.getCatName())) {
            boolQueryBuilder.must(QueryBuilders.termQuery(ReflectUtil.getFieldName(CatQuery::getCatName), catQuery.getCatName()));
        }
       //删除状态
        boolQueryBuilder.must(QueryBuilders.termQuery("deleted", 0));
        //时间 倒序
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(CREATE_TIME).order(SortOrder.DESC);
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.sort(sortBuilder);

        return sourceBuilder;
    }

}
