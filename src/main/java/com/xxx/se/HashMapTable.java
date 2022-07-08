package com.xxx.se;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author xqh
 * @date 2022/7/8  11:27:57
 * @apiNote
 */
public class HashMapTable {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();

        map.put("1","2");
        System.out.println(map.containsKey("1"));
        System.out.println(map.containsValue("2"));
//        线程安全
        Hashtable<String, String> table = new Hashtable<>();
        table.put("1","2");
        System.out.println(table.containsKey("1"));
        System.out.println(table.containsValue("2"));
        map.put(null,"null");
        map.put(null,null);
//        table.put(null,"");//table不能null

    }
}
