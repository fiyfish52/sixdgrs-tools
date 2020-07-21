package com.sixwork.sixdgrs.javase.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Test {
    public void excute(){
        System.out.println("hello");
    }
    @AutoCall
    void callExcute(){
        excute();
    }

}
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface AutoCall{

        }