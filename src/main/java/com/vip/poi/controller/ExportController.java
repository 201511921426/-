package com.vip.poi.controller;

import com.vip.poi.service.PortService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public String portXls(String path,String dir)throws Exception{
        //传入的目录要完整，比如:"F:/test/"
        portService.QueryAllToXls(path,dir);
        return "导出并压缩成功，请在传入的参数路径下查看文件！";
    }

    @RequestMapping("/csv")
    public String portCsv(String path,String dir)throws Exception{
        //传入的目录要完整，比如:"F:/test/"
        portService.QueryAllToCvs(path,dir);
        return "导出并压缩成功，请在传入的参数路径下查看文件！";
    }

}
