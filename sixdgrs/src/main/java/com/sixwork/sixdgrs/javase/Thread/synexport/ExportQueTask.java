package com.sixwork.sixdgrs.javase.Thread.synexport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 查询task
 */
public class ExportQueTask implements Callable<List<String>> {

    public ExportQueTask() throws Exception {
        super();
    }

    public List<String> findData() {
        List<String> infoList = new ArrayList<String>();
        for(int j=0;j<1000;j++) {
            String info = "";
            for (int i = 0; i < 100; i++) {
                info += "i~" + i + "|";
            }
            infoList.add(info);
        }
        return infoList;
    }

    @Override
    public List<String> call() throws Exception {
        return findData();
    }
}