package com.xxx.se.d01;

/**
 * @author xqh
 * @date 2022/7/8  11:56:12
 * @apiNote
 */
public class BuffBuild {
    public static void main(String[] args) {

//线程安全   方法大都采用了 synchronized
        StringBuffer buffer = new StringBuffer();
        buffer.append("");

//        线程不安全
        StringBuilder builder = new StringBuilder();
        builder.append("");

//        单线程程序下，StringBuilder效率更快，因为它不需要加锁


    }
}
