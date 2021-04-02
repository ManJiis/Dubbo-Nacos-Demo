package top.b0x0.admin.consumer.test.postconstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 查看@PostConstruct注解的加载顺序
 * 注解@DependsOn作用: 添加依赖的bean,先加载依赖的bean再加载此bean
 *
 * @author TANG
 */
@Component
@DependsOn(value = {"beanC"})
public class BeanA {

    @Autowired
    private BeanB beanB;

    @PostConstruct
    private void init() {
        System.out.println("这是Bean A 的 init 方法");
        beanB.testB();
    }

    public BeanA() {
        System.out.println("这是Bean A 的构造方法");
    }
}