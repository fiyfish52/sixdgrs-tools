package com.sixwork.sixdgrs.javase.reflection;

public class reflectionTest {

    public static void main(String argz[]) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Test t = (Test)Class.forName("Test").newInstance();
        
    }
}


class Test {
    public static Test test;
    public static Test getInstance(){
        if(test == null){
            return new Test();
        }
        return test;
    }
        }