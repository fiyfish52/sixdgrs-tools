package com.sixwork.sixdgrs.javase.reflection;

import java.lang.reflect.Method;

public class reflectionTest {

    public static void main(String argz[]) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
       try {
           Class cls = Class.forName("com.sixwork.sixdgrs.javase.reflection.Test");
           Method m = cls.getMethod("showInfo");
           m.invoke(cls.newInstance());
       }catch(Exception e){
           System.out.println(e);
       }
    }
}


class Test {
    public static Test test;

    public Test newInstance() {

        System.out.println("create");
        if (test == null) {
            test =  new Test();
        }
        return test;
    }

    public void showInfo() throws Exception {
        throw new Exception("heheda");
    }
}