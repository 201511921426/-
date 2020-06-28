package com.vip.poi.util;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/5/26 16:54
 * @features 定义excel描述注解
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

    /**
     * 列索引
     *
     * @return int
     */
    public int columnIndex() default 0;

    /**
     * 列名
     *
     * @return string
     */
    public String columnName() default "";

}
