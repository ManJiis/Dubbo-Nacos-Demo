package top.b0x0.admin.service.test;

import top.b0x0.admin.common.util.spring.SpringContextHolder;
import top.b0x0.admin.service.ServiceProvideApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProvideApplication.class)
public class ApplicationContextTest implements ApplicationContextAware {


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        beanNameSet.addAll(Arrays.asList(applicationContext.getBeanDefinitionNames()));
    }

    static Set<String> beanNameSet = new HashSet<>();

    @Test
    public void test1() {
        for (String s : beanNameSet) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void test2() {
        System.out.println("SpringContextHolder.isInBeanNameSet(\"objectRedisTemplate\") = " + SpringContextHolder.isInBeanNameSet("objectRedisTemplate"));
    }

}




