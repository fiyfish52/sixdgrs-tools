package com.sixwork.sixdgrs.javase.Thread.synexport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

public class ExportConsumerThread extends Thread{

    //Logger log = LoggerFactory.getLogger(ExportConsumerThread.class);

    private ArrayBlockingQueue<List<String>> queue;
    private Integer count;
    private Integer total;
    private File saveFile;
    private StringBuffer buffer;
    private volatile PrintWriter csvWriter;
    private volatile String fileName;
    public ExportConsumerThread(ArrayBlockingQueue<List<String>> queue, int total) {
        this.queue = queue;
        this.total = total;
        this.count = 0;
        this.fileName="zlp2.csv";
        this.saveFile = new File("D:\\export\\"+this.fileName);
        try {
            this.csvWriter = new PrintWriter(this.saveFile, "GBK");
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();

         //   log.info("找不到文件" + this.fileName);
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          //  log.info("不支持的文件类型");
        }
        this.buffer = new StringBuffer();
    }

    public void run(){
        try {
            while (true) {
               // sleep(500000);
                if (count >= total) {
                    csvWriter.close();
                    break;
                }
                System.out.println("3-"+count);
                List<String> pageQueue = queue.take();
                createCsvFile(pageQueue);
                csvWriter.write(buffer.toString());
                csvWriter.flush();
                count += 1;
            }
        } catch (Exception e) {
        }
    }

    private void createCsvFile(List<String> listStr) throws Exception {
        Long starttime = System.currentTimeMillis();
        this.buffer = new StringBuffer();
        String title[];

        for(String str:listStr){
            buffer.append(str);
            buffer.append("\r\n");
        }
    }
}
