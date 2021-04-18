package top.b0x0.admin.service.test;

import top.b0x0.admin.common.util.properties.CommonApplicationProperties;
import top.b0x0.admin.service.SdndServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Properties;

/**
 * propertise测试
 *
 * @author TANG
 * @date 2021-02-13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SdndServiceApplication.class)
public class PropertiesTest {

    @Test
    public void test1() {
        Properties properties = CommonApplicationProperties.getProperties();
        System.out.println("current ActEnv = " + properties.getProperty("oss.profiles.active"));
        for (Object key : properties.keySet()) {
            System.out.println(key + " = " + properties.get(key));
        }
    }
}
