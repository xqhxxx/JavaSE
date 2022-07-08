package com.xxx.se.d01;

import java.util.HashSet;
import java.util.TreeSet;

/**
 * @author xqh
 * @date 2022/7/8  11:38:29
 * @apiNote
 */
public class TreeSet_HashSet {
    public static void main(String[] args) {

//采用hash表  元素没有按顺序排列
        HashSet<String> hs = new HashSet<>();
        hs.add("s");
        hs.remove("2");

//树结构实现 红黑树算法   元素按顺序进行排列
        TreeSet<String> ts = new TreeSet<>();
        ts.add("1");
        ts.remove("2");

        //提供了一些方法来处理排序的set
        ts.first();
        ts.last();
//        ts.headSet();
//        ts.floor()
    }
}
