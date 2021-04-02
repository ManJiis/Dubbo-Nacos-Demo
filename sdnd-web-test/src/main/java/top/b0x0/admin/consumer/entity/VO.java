package top.b0x0.admin.consumer.entity;


/**
 * @author musui
 */
public class VO {

    private Integer id;
    private Integer age;

    // 实例初始化块（构造代码块)
    {
        System.out.println("1-->VO 普通代码块...");
    }

    // 静态初始化块（静态代码块）
    static {
        System.out.println("2-->VO 静态代码块...");
    }

    public VO() {
        System.out.println("3-->VO 无参构造函数....");
    }

    public VO(int id, int age) {
        super();
        this.age = age;
        this.id = id;
        System.out.println("4-->VO 有参参构造函数....");
        System.out.println("5-->VO 有参构造函数中的 age = " + this.age);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "VO[" +
                "id=" + id +
                ", age=" + age +
                ']';
    }
}