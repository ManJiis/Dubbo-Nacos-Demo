package top.b0x0.admin.consumer.test.hutool.crypto;

import cn.hutool.crypto.SecureUtil;
import top.b0x0.admin.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 摘要加密
 *
 * @author TANG
 * @date 2021-02-19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DigestTest {
    private final static Logger log = LoggerFactory.getLogger(DigestTest.class);

    //    密钥生成
    //        - SecureUtil.generateKey 针对对称加密生成密钥
    //        - SecureUtil.generateKeyPair 生成密钥对（用于非对称加密）
    //        - SecureUtil.generateSignature 生成签名（用于非对称加密）
    //  摘要算法
    //    - SecureUtil.md5
    //    - SecureUtil.sha1
    //    - SecureUtil.hmac
    //    - SecureUtil.hmacMd5
    //    - SecureUtil.hmacSha1

    @Test
    public void context() {
        String data = "haha嘻嘻";
        log.info("MD5前的内容 : {}", data);
        String string = SecureUtil.md5(data);
        log.info("MD5后的内容 : {}", string);
    }
}
