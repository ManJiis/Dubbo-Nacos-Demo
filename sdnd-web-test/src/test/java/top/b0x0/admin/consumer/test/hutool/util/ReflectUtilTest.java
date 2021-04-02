package top.b0x0.admin.consumer.test.hutool.util;

import cn.hutool.core.util.ReflectUtil;
import top.b0x0.admin.DemoApplication;
import top.b0x0.admin.consumer.entity.Demo2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;

/**
 * 反射类测试
 *
 * @author TANG
 * @date 2021-02-20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class ReflectUtilTest {

    @Test
    public void reflectTest() {
        Field[] fields = ReflectUtil.getFields(Demo2.class);
        for (Field field : fields) {
            field.setAccessible(true);
            String lowerCase = field.getName().toLowerCase();
            System.out.println("lowerCase = " + lowerCase);
            if (lowerCase.contains("url")) {
                System.out.println(field.getName() + " 图片地址...");
            } else {
                System.out.println(field.getName() + " 普通值...");
            }
        }
    }
}
