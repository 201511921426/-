package com.vip.poi.controller;

import com.vip.poi.service.PortService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/5/28 14:00
 * @features 请求入口
 */
@RestController
@RequestMapping
public class ExportController {

    @Resource
    PortService portService;

    @RequestMapping("/xls")
    public Object portXls(String path, String dir) throws Exception {
        //开始时间
        Long startTime = new Date(System.currentTimeMillis()).getTime();
        //传入的目录要完整，比如:"F:/test/"
        portService.QueryAllToXls(path, dir);
        //结束时间
        Long endtTime = new Date(System.currentTimeMillis()).getTime();

        return "导出并压缩成功，请在传入的参数路径下查看文件,一共用时：" + (endtTime - startTime) / 1000;
    }

    @RequestMapping("/csv")
    public String portCsv(String path, String dir) throws Exception {
        //传入的目录要完整，比如:"F:/test/"
        portService.QueryAllToCvs(path, dir);
        return "导出并压缩成功，请在传入的参数路径下查看文件！";
    }

}
