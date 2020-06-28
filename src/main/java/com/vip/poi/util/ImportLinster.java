package com.vip.poi.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.vip.poi.entity.Blog;
import com.vip.poi.entity.dto.ImportDto;
import com.vip.poi.mapper.BlogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/6/28 21:33
 * @features
 */

public class ImportLinster extends AnalysisEventListener<ImportDto> {
    Logger logger = LoggerFactory.getLogger(ImportLinster.class);

    public ImportLinster(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    private final BlogMapper blogMapper;

    @Override
    public void invoke(ImportDto importDto, AnalysisContext analysisContext) {
        Integer currentRowNum = analysisContext.getCurrentRowNum();
        logger.info("当前行为：{}",currentRowNum);
        int id = importDto.getId();
        String title = importDto.getTitle();
        String content = importDto.getContent();
        if (title!=null || content != null){
            Blog blog = blogMapper.selectById(id);
            if (blog==null){
                blogMapper.insert(title,content);
            }
        }else {
            throw new RuntimeException("参数不能全为空");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.info("导入成功！");
    }
}
