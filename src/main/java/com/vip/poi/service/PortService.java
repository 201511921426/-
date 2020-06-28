package com.vip.poi.service;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/5/26 16:54
 * @features
 */
public interface PortService {

    /**
     * @param path
     * @return
     * @throws Exception
     * @fetures 查询列表方法
     */
    void QueryAllToXls(String path, String dir) throws Exception;

    /**
     * @param path
     * @return
     * @throws Exception
     * @fetures 查询列表方法
     */
    void QueryAllToCvs(String path, String dir) throws Exception;
}
