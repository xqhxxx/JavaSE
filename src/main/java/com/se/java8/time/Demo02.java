package com.se.java8.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author xqh
 * @date 2023-02-02  16:13:33
 * @apiNote
 */
public class Demo02 {
    public static void main(String[] args) {


        // 时间戳互相转化  毫秒
        LocalDateTime now = LocalDateTime.now();

//        1675325664401
        long timestamp = now.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        LocalDateTime time2 = LocalDateTime.ofEpochSecond(timestamp / 1000,
                (int) ((timestamp % 1000) * 1000 * 1000), ZoneOffset.ofHours(8));

        LocalDateTime time3 = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());

        long l = now.toEpochSecond(ZoneOffset.of("+8"));

        System.out.println();
    }
}
