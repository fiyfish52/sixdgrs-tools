package com.sixwork.sixdgrs.javase.Thread.synexport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class start {


    public static void main(String[] aaaaa) throws Exception {
        System.out.print("begin");
        int taskTotal = 1000;
        int threadNum = 5;
        //ExecutorService cuser = Executors.newFixedThreadPool(5);
        ThreadPoolExecutor cuser = new ThreadPoolExecutor(5,5,3,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(2000));

        List<Future<List<String>>> futureResultList = new ArrayList<Future<List<String>>>(taskTotal);
        for(int i = 0;i<taskTotal;i++){
            System.out.println("1-"+i);
            ExportQueTask tsk = new ExportQueTask();
            Future<List<String>> futureResult = cuser.submit(tsk);
            futureResultList.add(futureResult);
        }
        cuser.shutdown();

        ArrayBlockingQueue<List<String>> queue = new ArrayBlockingQueue<List<String>>(20);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,5,3,TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(2000));

       // ExecutorService producerExecutorService = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < taskTotal; i++) {
            System.out.println("2-"+i);
            ExportProductThread producer = new ExportProductThread(queue, futureResultList.get(i));
            pool.submit(producer);
        }
        pool.shutdown();

        ExportConsumerThread consumer = new ExportConsumerThread(queue, taskTotal);
        System.out.println("3-");
        consumer.start();
        consumer.join();

    }
}
