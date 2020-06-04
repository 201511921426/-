package com.vip.poi.service.impl;

import com.vip.poi.entity.GcCommonDict;
import com.vip.poi.mapper.GcCommonDictMapper;
import com.vip.poi.service.PortService;
import com.vip.poi.util.CopyFile;
import com.vip.poi.util.PortUtils;
import com.vip.poi.util.ZipUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.vip.poi.util.Constants.*;
import static com.vip.poi.util.FileRecursiveTool.deleteDir;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/5/26 16:21
 * @features 逻辑处理层
 */
@Service
public class PortServiceImpl implements PortService {

    @Resource
    GcCommonDictMapper gcCommonDictMapper;

    @Resource
    PortUtils portUtils;

    @Resource
    CopyFile copyFile;

    /**
     *@fetures 业务层逻辑处理 导出execl表格
     *@param path
     *@return 对象列表
     *@throws Exception
     */
    @Override
    public void QueryAllToXls(String path,String dir)throws Exception {
        //查询数据库数据总量
        int count = gcCommonDictMapper.queryAll();
        //当数据库要导出的数据量大于5000000条时进行分页查询
        if (count > NUM_1000000){
            //分页查询，每次查出1000000条数据
            int size = NUM_100000;
            //查询数据库的次数
            int queryTime = count / size + (count % size > NUM_0 ? NUM_1 : NUM_0);
            //分页起始索引
            int startIndex = NUM_0;
            for (int j = 0 ; j < queryTime ; j++){
                //创建日期格式文件用给导出的文件命名
                Date currentime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String format = formatter.format(currentime);
                //dao层操作数据库
                List<GcCommonDict> list = gcCommonDictMapper.findAllByLimit(startIndex,size);
                //下一次从数据库里面查数据的起始索引
                startIndex = startIndex + size + NUM_1;
                if (list.size()>NUM_0){
                    //创建数据导入索引
                    int index = NUM_0;
                    //每个表格要装的数据量
                    int num = NUM_50000;
                    //循环次数
                    int quotient = list.size() / num + (list.size() % num > NUM_0 ? NUM_1 : NUM_0);
                    for (int i = 0;i < quotient-1;i++){
                        //因为数据量巨大，单个表格无法装下，通过截断获取一部分数据
                        List<GcCommonDict> list1 = list.subList(index, index + size-NUM_1);
                        //下一次截断的起始位置
                        index=index+num;
                        //文件名：时间+i，格式是.xlsx
                        String path_1 = path + "/" + format +i +".xlsx";
                        //调用outExcelFile方法生成execl表格放到path_1路径下
                        portUtils.outExcelFile(list1,path_1);
                    }
                    //一个文件数据装载量为50000，如果最后list数据量不足50000，继续装载有可能导致索引越界，以下是处理数据余量
                    List<GcCommonDict> list2 = list.subList(index, list.size());
                    //调用outExcelFile方法生成execl表格放到path_2路径下
                    String path_2 = path+"/"+ format + (quotient-NUM_1)+".xlsx";
                    portUtils.outExcelFile(list2,path_2);
                }else {
                    //调式控制台输出，上线调整为日志打印输出
                    System.out.println("空列表");
                }
            }
        }else {
            //创建日期格式文件用给导出的文件命名
            Date currentime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String format = formatter.format(currentime);
            //dao层操作数据库
            List<GcCommonDict> list = gcCommonDictMapper.findAll();
            if (list.size()>NUM_0){
                //单个文件要导入的数据量
                int size = NUM_500000;
                //创建数据导入索引
                int index = NUM_0;
                //循环次数
                int quotient = count / size + (count % size > NUM_0 ? NUM_1 : NUM_0);
                for (int i = 0;i < quotient-1;i++){
                    //因为数据量巨大，单个表格无法装下，通过截断获取一部分数据
                    List<GcCommonDict> list1 = list.subList(index, index + size-NUM_1);
                    //下一次截断的起始位置
                    index=index+size;
                    //文件名：时间+i，格式是.xls
                    String path_1 = path + "/" + format +i +".xlsx";
                    //调用outExcelFile方法生成execl表格放到path_1路径下
                    portUtils.outExcelFile(list1,path_1);
                }
                //一个文件数据装载量为50000，如果最后list数据量不足50000，继续装载有可能导致索引越界，以下是处理数据余量
                List<GcCommonDict> list2 = list.subList(index, count);
                //调用outExcelFile方法生成execl表格放到path_2路径下
                String path_2 = path+"/"+ format + (quotient-NUM_1)+".xlsx";
                portUtils.outExcelFile(list2,path_2);
            }else {
                //调式控制台输出，上线调整为日志打印输出
                System.out.println("空列表");
            }
        }
        //传入路径path与你想要新建的文件夹名dir，用于拼接一条新的路径
        String path_3= path+"/"+dir;
        File file = new File(path_3);
        if (!file.exists()){
            file.mkdir();
        }
        //调用opyFile工具，把生成在path路径下的.xls格式文件放到指定的文件夹中
        copyFile.copyFile(path,dir);
        //压缩上一步的文件夹
        ZipUtils zipUtils = new ZipUtils();
        InputStream inputStream =
                zipUtils.craeteZipPath(path_3);
        //因为只要压缩文件，中间过程生成的文件或者文件夹需要递归删除
        deleteDir(path);
        deleteDir(path_3);
    }

    /**
     *@fetures 业务层逻辑处理 导出csv文件
     *@param path
     *@return 对象列表
     *@throws Exception
     */
    @Override
    public void QueryAllToCvs(String path,String dir)throws Exception {
        //创建日期格式文件用给导出的文件命名
        Date currentime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = formatter.format(currentime);

        //dao层操作数据库
        List<GcCommonDict> list = gcCommonDictMapper.findAll();
        if (list.size()>0){
            //查询数据库数据总量
            int count = gcCommonDictMapper.queryAll();
            //单个文件要导入的数据量，单个execl表格最多只能导入六万多条数据
            int size = 50000;
            //创建数据导入索引
            int index = 0;
            //循环次数
            int quotient = count / size + (count % size > 0 ? 1 : 0);
            for (int i = 0;i < quotient-1;i++){
                //因为数据量巨大，单个表格无法装下，通过截断获取一部分数据
                List<GcCommonDict> list1 = list.subList(index, index + size-1);
                //下一次截断的起始位置
                index=index+size;
                //文件名：时间+i，格式是.xls
                String path_1 = path + "/" + format +i +".csv";
                //调用outExcelFile方法生成execl表格放到path_1路径下
                portUtils.outExcelFile(list1,path_1);
            }
            //一个文件的数据装载量为50000，如果最后list数据量不足50000，继续装载有可能导致索引越界，以下是处理数据余量
            List<GcCommonDict> list2 = list.subList(index, count);
            //调用outExcelFile方法生成execl表格放到path_2路径下
            String path_2 = path+"/"+ format + (quotient-1)+".csv";
            portUtils.outExcelFile(list2,path_2);
        }else {
            //调式控制台输出，上线调整为日志打印输出
            System.out.println("空列表");
        }
        //传入路径path与你想要新建的文件夹名dir，用于拼接一条新的路径
        String path_3= path+"/"+dir;
        File file = new File(path_3);
        file.mkdir();
        //调用opyFile工具，把生成在path路径下的.xls格式文件放到指定的文件夹中
        copyFile.copyFile(path,dir);
        //压缩上一步的文件夹
        ZipUtils zipUtils = new ZipUtils();
        InputStream inputStream =
                zipUtils.craeteZipPath(path_3);
        //因为只要压缩文件，中间过程生成的文件或者文件夹需要递归删除
        deleteDir(path);
        deleteDir(path_3);
    }

}


