package cn.tlh.admin.service.aop.annotaion;

import java.lang.annotation.*;

/**
 * @author TANG
 * @date 2020-13-19
 * description:  自定义注解，拦截controller
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})//作用在参数和方法上
@Retention(RetentionPolicy.RUNTIME)//运行时注解
@Documented//表明这个注解应该被 javadoc工具记录
public @interface Log {
    String description() default "";
}
