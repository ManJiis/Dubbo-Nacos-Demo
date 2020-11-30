package cn.tlh.ex.common.vo.req;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

/**
 * @author 蛮吉
 * @date: 2020/11/30
 * @description: Validated 参数校验示例
 */
public class SayHelloVO {
    String id;

    @NotBlank(message = "name不能为空")
//    @NotNull(message = "name不能为空")
    String name;

    @Future(message = "date需要一个将来日期") // 只能是将来的日期
    @Past //只能去过去的日期
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 日期格式化转换
//    @NotNull // 不能为空
    private Date date;

    //    @NotNull // 不能为空
    @DecimalMin(value = "0.1") // 最小值0.1元
    @DecimalMax(value = "10000.00") // 最大值10000元
    private Double doubleValue = null;

    @Min(value = 1, message = "integer最小值为1") // 最小值为1
    @Max(value = 88, message = "integer最大值为88") // 最大值88
//    @NotNull // 不能为空
    private Integer integer;

    @Range(min = 1, max = 150, message = "range范围为1至150") // 限定范围
    private Integer age;

    // 邮箱验证
    @Email(message = "email邮箱格式错误")
    private String email;

    @Size(min = 20, max = 30, message = "strSize字符串长度要求20到30之间")
    private String strSize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(Double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public Integer getInteger() {
        return integer;
    }

    public void setInteger(Integer integer) {
        this.integer = integer;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStrSize() {
        return strSize;
    }

    public void setStrSize(String strSize) {
        this.strSize = strSize;
    }

    @Override
    public String toString() {
        return "SayHelloVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", doubleValue=" + doubleValue +
                ", integer=" + integer +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", strSize='" + strSize + '\'' +
                '}';
    }
}
