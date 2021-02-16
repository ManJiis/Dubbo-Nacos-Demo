package cn.tlh.admin.consumer.aop.annotaion;


import java.lang.annotation.*;


/**
 * @author jacky
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    // 资源名称，用于描述接口功能
    String description() default "";

    // 资源 key
    String key() default "";

    // key prefix
    String keyPrefix() default "";

    // 时间的，单位秒
    int period();

    // 限制访问次数
    int count();

    // 限制类型
    LimitType limitType() default LimitType.CUSTOMER;

}
