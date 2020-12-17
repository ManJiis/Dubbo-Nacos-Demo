package cn.tlh.test.postconstruct;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author TANG
 */
@Component
public class BeanB {

    @PostConstruct
    private void init() {
        System.out.println("这是Bean B 的 init 方法");
    }

    public BeanB() {
        System.out.println("这是Bean B 的 构造方法");
    }

    void testB() {
        System.out.println("这是Bean B 的 testB 方法");
    }
}