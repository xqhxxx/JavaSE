package com.xxx.se.java8.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author xqh
 * @date 2022/8/17  16:14:01
 * @apiNote
 */
public class StreamDemo {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("张三","李四","王五","赵柳","张五六七6","王少","赵四","张仁","李星");
        //需求：找出 姓张中名字最长的
        String maxLengthStartWithZ = names
                .parallelStream()
                .filter(name -> name.startsWith("张"))
                .max(Comparator.comparing(x -> x.length()))
                .get();

        System.out.println(maxLengthStartWithZ);

//        names.stream().skip(2).forEach(System.out::println);
        System.out.println(names.stream().reduce((s, s2) -> s + "-" + s2).get());




    }
}
