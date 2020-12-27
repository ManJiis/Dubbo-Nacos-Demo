package cn.tlh.admin.consumer.test.postconstruct;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author TANG
 */
@Component
public class BeanC {

    @PostConstruct
    private void init() {
        System.out.println("这是Bean C 的 init 方法");
    }

    public BeanC() {
        System.out.println("这是Bean C 的 构造方法");
    }

    void testC() {
        System.out.println("这是Bean C 的 testC 方法");
    }
}