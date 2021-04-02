package top.b0x0.admin.consumer.test;

import top.b0x0.admin.DemoApplication;
import top.b0x0.admin.common.util.spring.SpringContextHolder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Set;

/**
 * spring工具类测试
 *
 * @author TANG
 * @date 2021-02-19
 */
@SpringBootTest(classes = DemoApplication.class)
public class SpringContextHolderTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void isInBeanNameSet() {
        Boolean dataSource = SpringContextHolder.isInBeanNameSet("dataSource");
        System.out.println("dataSource = " + dataSource);
    }

    @Test
    public void getBeanNameSet() {
        Set<String> beanNameSet = SpringContextHolder.getBeanNameSet();
        int i = 0;
        for (String s : beanNameSet) {
            i++;
            System.out.println("bean" + i + " = " + s);
        }
    }

    @Test
    public void mapTest() {
        HashMap<String, String> h = new HashMap<String, String>() {
            private static final long serialVersionUID = 6274888398954928088L;

            {
                put("a", "aaa");
                put("b", "bbb");
            }
        };
        System.out.println("h = " + h);
    }
}
