package com.chen.elasticsearch.query;

/**
 * CatQuery。
 *
 * @author xihaichen
 */
public class CatQuery extends PageQuery {

    private String catName;

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
