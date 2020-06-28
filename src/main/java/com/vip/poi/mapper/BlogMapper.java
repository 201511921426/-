package com.vip.poi.mapper;

import com.vip.poi.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/6/28 21:23
 * @features
 */
@Mapper
public interface BlogMapper {
    void insert(@Param("title")String title,@Param("content")String content);
    Blog selectById(@Param("id")int id);
}