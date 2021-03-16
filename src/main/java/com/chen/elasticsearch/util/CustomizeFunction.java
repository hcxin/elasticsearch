
package com.chen.elasticsearch.util;

import java.io.Serializable;
import java.util.function.Function;

/**
 * CustomizeFunction。
 *
 * @author xinhaichen
 */
@FunctionalInterface
public interface CustomizeFunction<T, R> extends Function<T, R>, Serializable {
}
