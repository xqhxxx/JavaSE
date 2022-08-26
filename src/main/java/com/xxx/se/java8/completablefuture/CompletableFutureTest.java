package com.xxx.se.java8.completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import static com.sun.corba.se.impl.orbutil.ORBUtility.getThreadName;

/**
 * @author xqh
 * @date 2022/8/25  09:58:41
 * @apiNote 异步编程
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        //todo 1 简单任务  主线程调用get阻塞子线程终止
       /* CompletableFuture<String> ctf = new CompletableFuture<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+"执行...");
                    ctf.complete("success");
                    //在子线程中完成主线程completableFuture的完成
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread t1 = new Thread(runnable);
        t1.start();

        //主线程阻塞，等待完成
        String result = ctf.get();
        System.out.println(Thread.currentThread().getName()+"-->"+result);
*/

        // TODO: 2  无返回值的异步任务
/*        CompletableFuture<Void> f = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println(getThreadName() + "正在执行一个没有返回值的异步任务。");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        f.get();
        System.out.println(getThreadName()+" end");
//CompletableFuture默认运行使用的是ForkJoin的的线程池。*/

        // TODO: 3 有返回值的
       /* CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(getThreadName() + "正在执行一个有返回值的异步任务");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return "ok";
            }
        });
        String s = future.get();
        System.out.println(getThreadName()+" 结果："+s);*/

        //上述用get 阻塞完成 其实还是同步的概念
        //todo 4 异步 回调函数  通过thenApply(), thenAccept()，thenRun()方法，来运行一个回调函数。

        //todo 4.1 thenApply(), 类似于spark的map算子 函数式编程
        /*CompletableFuture<String> task = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(getThreadName() + "supplyAsync");
                return "123";
            }
        });

        CompletableFuture<Integer> future =
                task.thenApply(x -> {
                    System.out.println(getThreadName() + "thenApple-1");
                    return Integer.parseInt(x);
                }).thenApply(x -> {
                    System.out.println(getThreadName() + "thenApple-2");
                    return x * 2;
                });
        System.out.println(getThreadName()+" => "+future.get());*/

        // todo 4.2 thenAccept 接受Futrue的一个返回值，但是本身不在返回任何值，适合用于多个callback函数的最后一步操作使用。
        CompletableFuture<String> task = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(getThreadName() + "supplyAsync");
                return "123";
            }
        });

        CompletableFuture<Void> result = task.thenApply(x -> {
            System.out.println(getThreadName() + "thenApply1");
            return Integer.parseInt(x);
        }).thenApply(x -> {
            System.out.println(getThreadName() + "thenApply2");
            return x * 2;
        }).thenAccept(x -> {
            System.out.println(getThreadName() + "thenAccept=" + x);
        });
        result.get();
        System.out.println(getThreadName()+"end");

    }


    static String getThreadName() {
        return Thread.currentThread().getName() + " 线程：";
    }
}
