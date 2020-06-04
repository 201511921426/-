package com.vip.poi.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.getInt;
import static javax.swing.UIManager.getString;
import static org.apache.commons.collections4.MapUtils.getDouble;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/5/26 16:11
 * @features 导入导出execl工具包
 */
@Component
public class PortUtils {

    /**
     * 输出excel文件
     * @param data 数据集合
     * @param path 输出路径
     */
    public void outExcelFile(List<?> data, String path) {

        File file = new File(path);

        // 创建workbook
//        HSSFWorkbook wb = new HSSFWorkbook();
        SXSSFWorkbook wb = new SXSSFWorkbook();


        // 创建sheet
        Sheet sheet = wb.createSheet("sheel");

        // 创建表头行
        Row row = sheet.createRow(0);

        // 创建单元格样式
//        HSSFCellStyle style = wb.createCellStyle();
        CellStyle style = wb.createCellStyle();

        // 居中显示
        style.setAlignment(HorizontalAlignment.CENTER);

        // 获取实体所有属性
        Field[] fields = data.get(0).getClass().getDeclaredFields();

        // 列索引
        int index = 0;

        // 列名称
        String name = "";
        MyAnnotation myAnnotation;

        // 创建表头
        for (Field f : fields) {

            // 是否是注解
            if (f.isAnnotationPresent(MyAnnotation.class)) {

                // 获取注解
                myAnnotation = f.getAnnotation(MyAnnotation.class);

                // 获取列索引
                index = myAnnotation.columnIndex();

                // 列名称
                name = myAnnotation.columnName();

                // 创建单元格
                creCell(row, index, name, style);
            }
        }

        // 行索引  因为表头已经设置，索引行索引从1开始
        int rowIndex = 1;
        for (Object obj : data) {

            // 创建新行，索引加1,为创建下一行做准备
            row = sheet.createRow(rowIndex++);
            for (Field f : fields) {

                // 设置属性可访问
                f.setAccessible(true);

                // 判断是否是注解
                if (f.isAnnotationPresent(MyAnnotation.class)) {

                    // 获取注解
                    myAnnotation = f.getAnnotation(MyAnnotation.class);

                    // 获取列索引
                    index = myAnnotation.columnIndex();
                    try {

                        // 创建单元格     f.get(obj)从obj对象中获取值设置到单元格中
                        creCell(row, index, String.valueOf(f.get(obj)), style);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            wb.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            //释放资源
            try {
                if (wb != null) {
                    try {
                        wb.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取excel文件，并把读取到的数据封装到clazz中
     *
     * @param path
     *            文件路径
     * @param clazz
     *            实体类
     * @return 返回clazz集合
     */
    public  <T extends Object> List<T> readExcelFile(String path, Class<T> clazz) {

        // 存储excel数据
        List<T> list = new ArrayList<>();
        FileInputStream is = null;

        try {
            is = new FileInputStream(new File(path));
        } catch (FileNotFoundException e1) {
            throw new RuntimeException("文件路径异常");
        }

        Workbook wookbook = null;

        // 根据excel文件版本获取工作簿
        if (path.endsWith(".xls")) {
            wookbook = xls(is);
        } else if (path.endsWith(".xlsx")) {
            wookbook = xlsx(is);
        } else {
            throw new RuntimeException("文件出错，非excel文件");
        }

        // 得到一个工作表
        Sheet sheet = wookbook.getSheetAt(0);

        // 获取行总数
        int rows = sheet.getLastRowNum() + 1;

        Row row;

        // 获取类所有属性
        Field[] fields = clazz.getDeclaredFields();

        T obj = null;
        int coumnIndex = 0;
        Cell cell = null;
        MyAnnotation myAnnotation = null;
        for (int i = 1; i < rows; i++) {
            // 获取excel行
            row = sheet.getRow(i);
            try {
                // 创建实体
                obj = clazz.newInstance();
                for (Field f : fields) {
                    // 设置属性可访问
                    f.setAccessible(true);
                    // 判断是否是注解
                    if (f.isAnnotationPresent(MyAnnotation.class)) {
                        // 获取注解
                        myAnnotation = f.getAnnotation(MyAnnotation.class);
                        // 获取列索引
                        coumnIndex = myAnnotation.columnIndex();
                        // 获取单元格
                        cell = row.getCell(coumnIndex);
                        // 设置属性
                        setFieldValue(obj, f, wookbook, cell);
                    }
                }
                // 添加到集合中
                list.add(obj);
            } catch (InstantiationException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }

        }

        try {
            //释放资源
            wookbook.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 设置属性值
     *
     * @param obj
     *            操作对象
     * @param f
     *            对象属性
     * @param cell
     *            excel单元格
     */
    private  void setFieldValue(Object obj, Field f, Workbook wookbook, Cell cell) {
        try {
            if (f.getType() == int.class || f.getType() == Integer.class) {
                f.setInt(obj, getInt(cell));
            } else if (f.getType() == Double.class || f.getType() == double.class) {
                f.setDouble(obj,getDouble(null, cell));
            } else {
                f.set(obj, getString(cell));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对excel 2003处理
     */
    private static Workbook xls(InputStream is) {
        try {
            // 得到工作簿
            return new HSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对excel 2007处理
     */
    private  Workbook xlsx(InputStream is) {
        try {
            // 得到工作簿
//            return new XSSFWorkbook(is);
            return new SXSSFWorkbook(new XSSFWorkbook(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建单元格
     *
     * @param row
     * @param c
     * @param cellValue
     * @param style
     */
    private  void creCell(Row row, int c, String cellValue, CellStyle style) {
        Cell cell = row.createCell(c);
        cell.setCellValue(cellValue);
        cell.setCellStyle(style);
    }

}
