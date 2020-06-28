package com.vip.poi.controller;

import com.vip.poi.service.ImportService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/6/28 22:03
 * @features
 */
@RestController
@RequestMapping("/import")
public class ImportController {
    @Resource
    ImportService importService;

    @RequestMapping("/point")
    public String insert()throws Exception{
        importService.insert();
        return "吃屎吧";
    }
}
