package com.vip.poi.util;

import org.springframework.stereotype.Component;

import java.io.*;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/6/02 16:54
 * @features 用于把某个文件夹里面特定的文件移动到特定的文件夹里面
 */
@Component
public class CopyFile {
    /**
     * @features 文件copy方法
     * @param path
     * @param dir
     * @throws IOException
     */
    public void copyFile(String path,String dir) throws IOException {
        //创建读写流
        FileInputStream fis;
        FileOutputStream fos;
        //缓存数组
        byte[]bs=new byte[1024*1024*2];
        //从以下路径复制文件
        File allFiles =new File(path);
        //文件过滤器
        FilenameFilter filter=new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                // TODO Auto-generated method stub
                return (name.endsWith(".csv")||name.endsWith(".xlsx")||name.endsWith(".xls"));
            }
        };
        //过滤文件存在str文件数组中
        File []str=allFiles.listFiles(filter);
        //遍历数组
        for(int i=0;i<str.length;i++) {
            //通过循环一个一个获取文件的名字
            String name=str[i].getName();
            //从oldFile取文件
            File oldFile =new File(path+"/"+name);
            //把文件存入newFile
            File newFile=new File(path+"/"+dir+"/"+name);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            //获取oldFile内的数据
            fis=new FileInputStream(oldFile);
            ///存储数据到newFile内（true为可以重复存取)
            fos=new FileOutputStream(newFile,true);
            //存储所有数据
            while(fis.read(bs)!=-1) {
                fos.write(bs);
            }
            //关闭流
            fis.close();
            fos.close();
        }
    }
}
