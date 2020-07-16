package com.sixwork.sixdgrs.javase.polymorphic;

/**
 * 创建一个Cycle类，它具有子类 Uncicycle,bicycle,trucycle .
 * 演示每一个类型的实例都由ride()方法向上转型为Cycle
 */
public class Show {
    public static void show(Cycle c){
        c.rade();
    }

    public static void main(String[] argz){
        Unicycle uni = new Unicycle();
        Show.show(uni);
        Bicycle bi = new Bicycle();
        Show.show(bi);
    }
}
class Cycle{

    public void wheels(){
        System.out.print("whells:cycle");
    }
    public void rade(){
        this.wheels();
    }
}
class Unicycle extends Cycle{
//    @Override
//    public void rade(){
//        System.out.println("Unicycle:rade()");
//    }

    @Override
    public void wheels(){
        System.out.println("Unicycle:wheels");
    }
}
class Bicycle extends Cycle{
//    @Override
//    public void rade(){
//        System.out.println("Bicycle:rade()");
//    }

    @Override
    public void wheels(){
        System.out.println("Bicycle:wheels");
    }
}