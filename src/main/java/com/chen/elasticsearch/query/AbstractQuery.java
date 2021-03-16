package com.chen.elasticsearch.query;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AbstractQuery。
 *
 * @author xihaichen
 */
public abstract class AbstractQuery implements Serializable {
    /**
     * 默认的属性个数。
     */
    private static final int INIT_ATTRIBUTE_CAPACITY = 16;

    /**
     * 自定义的属性表。
     */
    @JsonIgnore
    private final transient Map<String, Object> attributes = new ConcurrentHashMap<>(INIT_ATTRIBUTE_CAPACITY);

}
