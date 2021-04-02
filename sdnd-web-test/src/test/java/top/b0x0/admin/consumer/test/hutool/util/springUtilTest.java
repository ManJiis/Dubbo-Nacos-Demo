package top.b0x0.admin.consumer.test.hutool.util;

import cn.hutool.extra.spring.SpringUtil;
import top.b0x0.admin.DemoApplication;
import top.b0x0.admin.consumer.entity.Demo2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * springUtil工具类测试
 *
 * @author TANG
 * @date 2021-02-19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class springUtilTest {

    /**
     * 获取bean
     */
    @Test
    public void getBean() {
        final Demo2 testDemo = SpringUtil.getBean("testDemo");
        System.out.println("testDemo = " + testDemo);
    }
}
