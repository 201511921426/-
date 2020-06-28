package com.vip.poi.mapper;

import com.vip.poi.entity.GcCommonDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/5/26 16:42
 * @features dao层
 */
@Mapper
public interface GcCommonDictMapper {

    /**
     * @param
     * @return GcCommonDict对象列表
     * @fetures 数据量在5000000以内一次性查询所有数据
     */
    List<GcCommonDict> findAll();

    /**
     * @param
     * @return GcCommonDict对象列表
     * @fetures 当数据量超过5000000条，分多次查询所有数据
     */
    List<GcCommonDict> findAllByLimit(@Param("index") int index, @Param("size") int size);

    /**
     * @param
     * @return
     * @fetures 插入数据
     */
    void insert();

    /**
     * @param
     * @return int
     * @fetures 查询数据库数据总量
     */
    int queryAll();


}