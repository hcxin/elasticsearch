package com.chen.elasticsearch.controller;

import com.chen.elasticsearch.query.CatQuery;
import com.chen.elasticsearch.query.PageQuery;
import com.chen.elasticsearch.service.ElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ElasticController。
 *
 * @author xihaichen
 */
@RestController
@RequestMapping("/elastic")
public class ElasticController {

    @Autowired
    private ElasticService elasticService;

    @PostMapping("/getCatList")
    public void getCatList(@RequestBody CatQuery catQuery) {
         elasticService.getCatList(catQuery);
    }

}
