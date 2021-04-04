package top.b0x0.admin.consumer.annotaion;

import java.lang.annotation.*;

/**
 * @author jacky
 *  用于标记匿名访问方法
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousAccess {

}
