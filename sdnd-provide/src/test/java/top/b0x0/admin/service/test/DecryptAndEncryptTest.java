package top.b0x0.admin.service.test;

import top.b0x0.admin.common.util.EncryptUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 加解密测试
 *
 * @author TANG
 * @date 2021-02-16
 */
@SpringBootTest
public class DecryptAndEncryptTest {

    @Test
    public void test1() throws Exception {
        String decryptPass = EncryptUtils.desDecrypt("11F2DE5176D49691F9EE948AB7C56E52FE9E07F8F2A5F02B");
        System.out.println("decryptPass = " + decryptPass);
    }
}
