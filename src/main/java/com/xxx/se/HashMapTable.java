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
        table.contains("2");
        System.out.println(table.containsValue("2"));
        map.put(null,"null");
        map.put(null,null);
//        table.put(null,"");//table不能null

//
        /*数组初始化和扩容机制
HashTable在不指定容量的情况下的默认容量为11，而HashMap为16，
Hashtable不要求底层数组的容量一定要为2的整数次幂，而HashMap则要求一定为2的整数次幂。
Hashtable扩容时，将容量变为原来的2倍加1，而HashMap扩容时，将容量变为原来的2倍。
*/
    }
}
