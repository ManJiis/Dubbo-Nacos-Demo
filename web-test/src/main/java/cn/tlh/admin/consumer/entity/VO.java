package cn.tlh.admin.consumer.entity;


/**
 * @author musui
 */
public class VO {

    private Integer age;

    // 普通代码块
    {
        System.out.println("1-->VO 普通代码块...");
    }

    // 静态代码块
    static {
        System.out.println("2-->VO 静态代码块...");
    }

    public VO() {
        super();
        System.out.println("3-->VO 无参构造函数....");
    }

    public VO(int age) {
        super();
        this.age = age;
        System.out.println("4-->VO 有参参构造函数....");
        System.out.println("5-->VO 有参构造函数中的 age = " + this.age);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "VO [age=" + age + "]";
    }

}