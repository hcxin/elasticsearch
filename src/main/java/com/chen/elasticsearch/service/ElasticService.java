package com.chen.elasticsearch.service;

import com.chen.elasticsearch.query.CatQuery;

/**
 * ElasticService。
 *
 * @author xihaichen
 */
public interface ElasticService {

    void getCatList(CatQuery catQuery);
}
