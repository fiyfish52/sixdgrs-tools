package com.sixwork.sixdgrs;

import java.text.NumberFormat;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println((float)14+1);
        rate();
    }
    
    public static void rate() {
    	NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		String rate = numberFormat.format((float) (14+1) / (float) 23 * 100);//所占百分比
		System.out.println(rate);
    }
}
