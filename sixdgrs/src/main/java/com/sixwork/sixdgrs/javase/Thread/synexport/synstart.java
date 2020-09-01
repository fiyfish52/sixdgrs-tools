package com.sixwork.sixdgrs.javase.Thread.synexport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class synstart {
    public static void main(String[] aaaaa) throws Exception {
        System.out.println("begin");
        int taskTotal = 10000;
        int threadNum = 5;
       // ExecutorService cuser = Executors.newFixedThreadPool(5);
        ThreadPoolExecutor cuser2 = new ThreadPoolExecutor(5,5,3,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(20));

        List<Future<List<String>>> futureResultList = new ArrayList<Future<List<String>>>(taskTotal);
        ArrayBlockingQueue<List<String>> queue = new ArrayBlockingQueue<List<String>>(20);

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(5, 5,
                30, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(20),new ThreadPoolExecutor.CallerRunsPolicy());
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,5,3,
                TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(20),new ThreadPoolExecutor.CallerRunsPolicy());

        //启动文件写入
        ExportConsumerThread consumer = new ExportConsumerThread(queue, taskTotal);
        System.out.println("3-");
        consumer.start();

        for (int i = 0; i < taskTotal; i++) {
            while(queue.remainingCapacity()<1) {
                Thread.sleep(5000);
            }
            //分片任务
            System.out.println("1-"+i);
            ExportQueTask tsk = new ExportQueTask();
            Future<List<String>> futureResult = executorService.submit(tsk);
            futureResultList.add(futureResult);
            //获取数据
            System.out.println("2-"+i);
            ExportProductThread producer = new ExportProductThread(queue, futureResultList.get(i));
            pool.submit(producer);
        }
        executorService.shutdown();
        pool.shutdown();
    }
}
