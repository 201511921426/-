package com.vip.poi;

import com.vip.poi.mapper.GcCommonDictMapper;
import com.vip.poi.service.PortService;
import com.vip.poi.util.ZipUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PoiApplicationTests {

    @Resource
    PortService portService;

    @Resource
    GcCommonDictMapper gcCommonDictMapper;

    @Test
    public void test(){

        for (int i = 0;i<=100000;i++){
            gcCommonDictMapper.insert();
        }

    }

    @Test
    public void test1(){
        List list= new ArrayList<>();
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        Object o = list.get(4);
        System.out.println(o);

        List list1 = list.subList(0, 4);
        System.out.println(list1);
    }

    @Test
    public void test3()throws IOException{

                FileInputStream fis;
                FileOutputStream fos;
                //缓存数组
                byte[]bs=new byte[1024*1024*2];
                //从以下路径复制图片
                File allFiles =new File("F:\\");
                //图片过滤器
                FilenameFilter filter=new FilenameFilter() {

                    @Override
                    public boolean accept(File dir, String name) {
                        // TODO Auto-generated method stub
                        return (name.endsWith(".csv")||name.endsWith(".xls"));
                    }
                };
                //过滤图片存在str文件数组中
                File []str=allFiles.listFiles(filter);
                //遍历数组
                for(int i=0;i<str.length;i++) {
                    //通过循环一个一个获取图片的名字
                    String name=str[i].getName();
                    //从oldFile取图片
                    File oldFile =new File("F:\\"+name);
                    //把图片存入newFile
                    File newFile=new File("F:\\1111\\"+name);
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



