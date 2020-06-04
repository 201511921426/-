package com.vip.poi.mapper;

import com.vip.poi.entity.GcCommonDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author  wangdaye
 * @date  2020/5/26 16:42
 * @version 1.0
 * @features dao层
 */
@Mapper
public interface GcCommonDictMapper {

    /**
     *@fetures 数据量在5000000以内一次性查询所有数据
     *@param
     *@return GcCommonDict对象列表
     */
    List<GcCommonDict> findAll();

    /**
     *@fetures 当数据量超过5000000条，分多次查询所有数据
     *@param
     *@return GcCommonDict对象列表
     */
    List<GcCommonDict> findAllByLimit(@Param("index") int index, @Param("size") int size);

    /**
     *@fetures 插入数据
     *@param
     *@return
     */
    void insert();

    /**
     *@fetures 查询数据库数据总量
     *@param
     *@return int
     */
    int queryAll();


}