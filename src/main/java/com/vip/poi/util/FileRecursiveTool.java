package com.vip.poi.util;

import java.io.File;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/5/28 16:16
 * @features 文件和文件夹递归工具
 */
public class FileRecursiveTool {

    /**
     * 递归删除文件夹
     * @param dirPath 文件夹路径
     */
    public static void deleteDir(String dirPath) {
        File file = new File(dirPath);
        //判断当前file是否是文件，是就删除
        if (file.isFile()) {
            file.delete();
        } else {
            //否则是文件夹，获取当前文件夹里面所有文件和子文件夹扔到文件数组里面
            File[] files = file.listFiles();
            //如果当前文件夹是空文件夹，就直接把当前文件夹删除
            if (files == null) {
                file.delete();
            } else {
                //否则，遍历文件数组，然后递归本方法
                for (int i = 0; i < files.length; i++) {
                    deleteDir(files[i].getAbsolutePath());
                }
                //当递归本方法删除所有子文件和子文件夹后，删除当前文件夹
                file.delete();
            }
        }
    }
}
