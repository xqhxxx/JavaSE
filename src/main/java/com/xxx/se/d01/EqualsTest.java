package com.xxx.se.d01;

/**
 * @author xqh
 * @date 2022/7/8  14:28:30
 * @apiNote
 */
public class EqualsTest {
    public static void main(String[] args) {

//   == : 如果比较的是基本数据类型，那么比较的是变量的值
//如果比较的是引用数据类型，那么比较的是地址值（两个对象是否指向同一块内存）
//equals:如果没重写equals方法比较的是两个对象的地址值。
//如果重写了equals方法后我们往往比较的是对象中的属性的内容


// 1 、如果是基本类型比较，那么只能用==来比较，不能用equals
        int a = 3;
        int b = 4;
        int c = 3;
        System.out.println(a == b);//结果是false
        System.out.println(a == c);//结果是true
//        System.out.println(a.equals(c));//错误，编译不能通过，equals方法

//2、对于基本类型的包装类型，比如Boolean、Character、Byte、Shot、Integer、Long、Float、
// Double等的引用变量，==是比较地址的，而equals是比较内容的
        Integer n1 = new Integer(30);
        Integer n2 = new Integer(30);
        Integer n3 = new Integer(31);
        System.out.println(n1 == n2);//结果是false 两个不同的Integer对象，故其地址不同，
        System.out.println(n1 == n3);//那么不管是new Integer(30)还是new Integer(31) 结果都显示false
        System.out.println(n1.equals(n2));
        //结果是true 根据jdk文档中的说明，n1与n2指向的对象中的内容是相等的，都是30，故equals比较后结果是true
        System.out.println(n1.equals(n3));//结果是false 因对象内容不一样，一个是30一个是31


        System.out.println("*****************3***********");
//        3 字符串    包装类   new 的对象

        String aa =new String("a");
        String bb =new String("a");

        System.out.println(aa==bb);

//        equals 默认==
//         public boolean equals(Object obj) {
//        return (this == obj);
//    }





    }

}
