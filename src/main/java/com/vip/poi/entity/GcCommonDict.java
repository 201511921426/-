package com.vip.poi.entity;

import com.vip.poi.util.MyAnnotation;
import lombok.Data;

/**
 * @author  wangdaye
 * @date  2020/5/26 16:12
 * @version 1.0
 * @features 数据库实体映射类
 */
@Data
public class GcCommonDict {
    //描述改属性在excel中第0列，列名为 ：主键
    @MyAnnotation(columnIndex = 0,columnName = "主键")
    private Integer id;

    //描述改属性在excel中第1列，列名为 ：父键
    @MyAnnotation(columnIndex = 1,columnName = "父键")
    private Integer parentid;

    //描述改属性在excel中第2列，列名为 ：code
    @MyAnnotation(columnIndex = 2,columnName = "code")
    private String dictcode;

    //描述改属性在excel中第3列，列名为 ：行动名
    @MyAnnotation(columnIndex = 3,columnName = "行动名")
    private String dictname;

    //描述改属性在excel中第4列，列名为 ：级别
    @MyAnnotation(columnIndex = 4,columnName = "级别")
    private Byte level;

    //描述改属性在excel中第5列，列名为 ：是否使用
    @MyAnnotation(columnIndex = 5,columnName = "是否使用")
    private String isused;

    //描述改属性在excel中第6列，列名为 ：是否编辑
    @MyAnnotation(columnIndex = 6,columnName = "是否编辑")
    private String isedit;

    //描述改属性在excel中第7列，列名为 ：类型名
    @MyAnnotation(columnIndex = 7,columnName = "类型名")
    private String domainname;
}