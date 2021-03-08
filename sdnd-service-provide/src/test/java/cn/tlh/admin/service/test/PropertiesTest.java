package cn.tlh.admin.service.test;

import cn.tlh.admin.common.util.properties.ActEnvProperties;
import cn.tlh.admin.service.serviceImpl.ServiceProvideApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

/**
 * @author TANG
 * @description propertise测试
 * @date 2021-02-13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProvideApplication.class)
public class PropertiesTest {

    @Test
    public void test1() {
        Properties properties = ActEnvProperties.getProperties();
        System.out.println("current ActEnv = " + properties.getProperty("ActEnv"));
    }
}
