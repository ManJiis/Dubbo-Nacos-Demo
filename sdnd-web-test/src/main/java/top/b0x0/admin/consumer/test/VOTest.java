package top.b0x0.admin.consumer.test;

import top.b0x0.admin.consumer.entity.VO;

/**
 * 静态代码块、代码块、构造函数、匿名内部类、匿名内部类中的代码块
 */
public class VOTest {

    // 普通代码块
    {
        System.out.println("7-->Test 普通代码块....");
    }

    // 静态代码块
    static {
        System.out.println("8-->Test 静态代码块....");
    }

    // main方法
    public static void main(String[] args) {
        // 声明
        VO vo = null;
        // 匿名内部类
        vo = new VO(1, 21) {
            // 第一层花括号定义了一个继承于VO的匿名内部类 (Anonymous Inner Class), 继承VO对象。
            // 第二层花括号实际上是这个匿名内部类实例初始化块 (Instance Initializer Block)（或称为非静态初始化块）
            {
                // 实例初始化块（构造代码块)
                System.out.println("6-->匿名内部类 普通代码块....");
                // 年龄增加10
                this.setAge(this.getAge() + 10);
            }
            /*
            // 匿名内部类中的静态代码块, 编辑不能通过
            static { }
            */

        };

        // VO [age=31]
        System.out.println(vo);
    }
}