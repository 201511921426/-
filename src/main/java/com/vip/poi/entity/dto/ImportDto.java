package com.vip.poi.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/6/28 21:25
 * @features
 */
@Data
public class ImportDto extends BaseRowModel {
    @ExcelProperty(index = 0)
    private int id;
    @ExcelProperty(index = 1)
    private String title;

    @ExcelProperty(index = 2)
    private String content;
}
