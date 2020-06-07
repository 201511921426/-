package com.vip.poi.util;

/**
 * @author wangdaye
 * @version 1.0
 * @date 2020/6/5 14:56
 * @features
 */
public class ThreadUtils extends Thread {
        // 通过构造方法给线程名字赋值
        public ThreadUtils(String name) {
            super(name);// 给线程名字赋值
        }
        // 创建一个静态钥匙
        static Object ob = new Object();//值是任意的

        // 重写run方法
        @Override
        public void run() {

                synchronized (ob) {// 这个很重要，必须使用一个锁，

            }
        }
    }

