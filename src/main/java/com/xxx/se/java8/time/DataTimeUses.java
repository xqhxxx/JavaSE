package com.xxx.se.java8.time;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author xqh
 * @date 2022/8/16  14:47:20
 * @apiNote java.time 包下的所有类都是不可变类型而且线程安全。
 */
public class DataTimeUses {
    public static void main(String[] args) {

        //1.获取当前日期
        LocalDate current_day = LocalDate.now();
        System.out.println("1、当前日期：" + current_day);

        //2、获取年月日
        current_day.getYear();
        current_day.getMonthValue();
        current_day.getDayOfMonth();

        //3 指定日期
        LocalDate ud_day = LocalDate.of(2022, 5, 14);
        System.out.println("自定义日期：" + ud_day);

        //4 比较日期相等
        if (current_day.equals(ud_day)) {
            System.out.println("时间相等");
        } else {
            System.out.println("时间不等");
        }

        //5 周期事件 如生日
        MonthDay birthday = MonthDay.of(ud_day.getMonth(), ud_day.getDayOfMonth());
        MonthDay currentMd = MonthDay.from(current_day);
        if (currentMd.equals(birthday)) {
            System.out.println("is you bir");
        } else {
            System.out.println("is not");
        }

        //6获取当前时间
        System.out.println("6、当前时间，不含日期: " + LocalTime.now());
        System.out.println("6.1、当前日期时间: " + LocalDateTime.now());

        //7 对日期进行计算 +plus -minus
        LocalTime time = LocalTime.now();
        System.out.println("7.三个小时后的时间：" + time.plusHours(3));

        LocalDate day = LocalDate.now();
        System.out.println("7.1一周后后的时间：" + day.plus(1, ChronoUnit.WEEKS));
        //ChronoUnit  时间单位类 可以+ 月 年 时 分钟 十年 世纪

        System.out.println("8.一年前的日期：" + day.minus(1, ChronoUnit.YEARS));


        ///9.Clock时钟类 获取当时的时间戳，或当前时区下的日期时间信息。
        System.out.println(System.currentTimeMillis());
//        System.out.println(TimeZone.getDefault());

        Clock clock = Clock.systemUTC();
        System.out.println("clock: " + clock.millis());

        Clock defaultClock = Clock.systemDefaultZone();
        System.out.println("Clock: " + defaultClock.millis());


        //10 判断日期的早晚  前后
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = LocalDate.of(2023, 5, 14);
        if (tomorrow.isAfter(today)) {
            System.out.println("之后的日期:" + tomorrow);
        }
        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
        if (yesterday.isBefore(today)) {
            System.out.println("之前的日期:" + yesterday);
        }

        //11 处理时区
        // Date and time with timezone in Java 8
        ZoneId america = ZoneId.of("America/New_York");
        LocalDateTime localDateAndTime = LocalDateTime.now();
        ZonedDateTime dateAndTimeInNewYork = ZonedDateTime.of(localDateAndTime, america);
        System.out.println(" particular timezone : " + dateAndTimeInNewYork);


        //12 如何表示信用卡到期这类固定日期，答案就在 YearMonth
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
        YearMonth creditCardExpiry = YearMonth.of(2022, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s %n", creditCardExpiry);

        // 闰年
        if (day.isLeapYear()) {
            System.out.println("This year is Leap year");
        } else {
            System.out.println("2022 is not a Leap year");
        }

        //计算两个日期之间的天数和月数 java.time.Period
        // 各计算各的 8月16日 差的天数是2
        today = LocalDate.now();
        LocalDate java8Release = LocalDate.of(2022, 5, 14);
        Period pd = Period.between(today, java8Release);
        System.out.println("相差：年 月 日" + pd.getYears() + " " + pd.getMonths() + " " + pd.getDays());


        //取当前的时间戳  Date 类和 Instant 类各自的转换方法互相转换。
        Instant tm = Instant.now();
        System.out.println("当前时间戳："+tm.toEpochMilli());


        //日期格式化

        String dt="20220514";
//        DateTimeFormatter.BASIC_ISO_DATE
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

        System.out.println("字符转日期："+LocalDate.parse(dt,dtf));
        System.out.println("日期转字符："+day.format(dtf));

        DateTimeFormatter dtfs = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");

        System.out.println(LocalDateTime.now().format(dtfs));

    }
}
