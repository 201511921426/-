package com.vip.poi.service.impl;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.vip.poi.entity.dto.ImportDto;
import com.vip.poi.mapper.BlogMapper;
import com.vip.poi.service.ImportService;
import com.vip.poi.util.ImportLinster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/6/28 21:41
 * @features
 */
@Service("ImportService")
public class ImportServiceImpl implements ImportService {
    Logger logger = LoggerFactory.getLogger(ImportServiceImpl.class);
    @Resource
    private BlogMapper blogMapper;
    @Override
    public void insert() throws IOException{
        FileInputStream in = new FileInputStream("C:/Users/WCY/Desktop/积分导入.xlsx");
        try {
            ImportLinster importLinster = new ImportLinster(blogMapper);
            EasyExcelFactory.readBySax(in,new Sheet(1,1, ImportDto.class),importLinster);
        }catch (Exception e){
            logger.info("11111111111");
        }finally {
            in.close();
        }

    }
}
