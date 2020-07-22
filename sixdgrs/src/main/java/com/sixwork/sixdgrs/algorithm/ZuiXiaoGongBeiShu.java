package com.sixwork.sixdgrs.algorithm;

import java.util.Scanner;

/**
 * （a*b）/最大公约数 = a,b的最小公倍数
 */
public class ZuiXiaoGongBeiShu {


    /**
     * 求最大公约数
     * @param a
     * @param b
     * @return
     */
    public static Integer CalcMixLcm(Integer a, Integer b) {
        if(a < b){
            int t = a;
            a = b;
            b = t;
        }
        while(b != 0){
            if(a == b){
                return a;
            }else{
                int k = a % b;
                a = b;
                b = k;
            }
        }
        return a;
    }

    public static void main(String[] argzs) {
        Scanner scanner = new Scanner(System.in);
        Integer a[]={null,null};
        for(int i=0;i<2;i++){
            while(true){
                try{
                    System.out.println("请输入第"+(i+1)+"个正整数（回车结束）：");
                   a[i] = scanner.nextInt();
                   break;
                }catch (Exception e){
                    System.out.print("输入错误,");
                    scanner.next();
                }
            }
        }

        scanner.close();

        Integer ret = CalcMixLcm(a[0],a[1]);
        System.out.println(a[0]+"与"+a[1]+"的最小公倍数为："+(a[0]*a[1]/ret));

    }
}
