package com.vip.poi.service;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/5/26 16:54
 * @features
 */
public interface PortService {

    /**
     *@fetures 查询列表方法
     *@param path
     *@return
     *@throws Exception
     */
    void QueryAllToXls(String path,String dir) throws Exception;

    /**
     *@fetures 查询列表方法
     *@param path
     *@return
     *@throws Exception
     */
    void QueryAllToCvs(String path,String dir) throws Exception;
}
