package com.sixwork.sixdgrs.javase.polymorphic;

/**
 * 类加载顺序
 * (1) 父类静态代码块(包括静态初始化块，静态属性，但不包括静态方法)
 * (2) 子类静态代码块(包括静态初始化块，静态属性，但不包括静态方法 )
 * (3) 父类非静态代码块( 包括非静态初始化块，非静态属性 )
 * (4) 父类构造函数
 * (5) 子类非静态代码块 ( 包括非静态初始化块，非静态属性 )
 * (6) 子类构造函数
 */
public class Polymorphic {
    private String baseName = "base";
    public Polymorphic() {
        callName2();
    }
    public void callName2() {
        System.out.println(baseName);
    }
    static class Sub extends Polymorphic {
        private String baseName = "sub";
        public void callName() {
            System.out.println(baseName);
        }
    }

    public static void main(String[] args) {
        Polymorphic b = new Sub();

//        执行 Base b = new Sub();时由于多态 b编译时表现为Base类特性，运行时表现为Sub类特性，
//        Base b = new Sub()；不管是哪种状态都会调用Base构造器执行 callName()方法；
//        执行方法时，由于多台表现为子类特性，所以会先在子类是否有 callName()；
//        而此时子类尚未初始化（执行完父类构造器后才会开始执行子类），
//        如果有就执行，没有再去父类寻找
//        如果把父类 callName()改为 callName2()，则会输出base
    }
}
