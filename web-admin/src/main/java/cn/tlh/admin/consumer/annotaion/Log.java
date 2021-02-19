package cn.tlh.admin.consumer.annotaion;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author TANG
 * @date 2020-13-19
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用在参数和方法上
@Retention(RetentionPolicy.RUNTIME)//运行时注解
@Documented//表明这个注解应该被 javadoc工具记录
public @interface Log {
    String description() default "";
}
