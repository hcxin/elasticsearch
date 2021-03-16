package com.chen.elasticsearch.query;

/**
 * PageQuery。
 *
 * @author xihaichen
 */
public class PageQuery extends AbstractQuery {
    /**
     * 页数
     */
    private Integer pageNum;

    /**
     * 查询条数
     */
    private Integer pageSize;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
