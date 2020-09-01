package com.sixwork.sixdgrs.javase.Thread.synexport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;

public class ExportProductThread extends Thread {
    private ArrayBlockingQueue<List<String>> queue;
    private Future<List<String>> futureResult;
   // Logger log = LoggerFactory.getLogger(ExportProductThread.class);
    public ExportProductThread(ArrayBlockingQueue<List<String>> queue, Future<List<String>> futureResult) {
        this.queue = queue;
        this.futureResult = futureResult;
    }

    public void run() {

        while (true) {
            try {
                if (futureResult.isCancelled()) {
                    break;
                }
                if (futureResult.isDone()) {
                    // 如果队列已满，生产者休眠
//                    if (queue.remainingCapacity() < 1) {
//                        System.out.println("睡眠"+queue.size());
//                        //Thread.sleep(2000);
//
//                        continue;
//                    }
                    List<String> str = futureResult.get();
                    // 将数据放入阻塞队列
                    queue.put(str);
                    break;
                } else {
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                break;
            }
        }

    }

}