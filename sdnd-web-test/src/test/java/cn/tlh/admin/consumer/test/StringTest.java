package cn.tlh.admin.consumer.test;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author TANG
 * @date 2021-02-16
 */
@SpringBootTest
public class StringTest {

    @Test
    public void test1() {
        String s = "limit:test:_system_limit";
        String s1 = s.replaceFirst("_", "");
        System.out.println("s1 = " + s1);
    }

}
