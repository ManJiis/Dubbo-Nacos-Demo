package top.b0x0.admin.consumer.test.hutool.crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import top.b0x0.admin.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * 对称加密
 * 加解密使用的是同一个秘钥
 * 基于“对称密钥”的加密算法主要有DES、3DES（TripleDES）、AES、RC2、RC4、RC5和Blowfish等。
 * <p>
 * 最常用的对称加密算法有DES、3DES（TripleDES）和AES。
 *
 * @author TANG
 * @date 2021-02-19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class SymmetricTest {
    private final static Logger log = LoggerFactory.getLogger(SymmetricTest.class);
    //    密钥生成
    //        - SecureUtil.generateKey 针对对称加密生成密钥
    //        - SecureUtil.generateKeyPair 生成密钥对（用于非对称加密）
    //        - SecureUtil.generateSignature 生成签名（用于非对称加密）
    //    对称加密
    //        - SecureUtil.aes
    //        - SecureUtil.des

    public String generateAesKey() {
        // 密钥生成  针对对称加密生成密钥
        SecretKey secretKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue());
        byte[] secretKeyEncoded = secretKey.getEncoded();
        //将秘钥转为Base64
        return encode(secretKeyEncoded);
    }

    public String encode(byte[] bytes) {
        return Base64.encode(bytes);
    }

    public byte[] decode(String bytes) {
        return Base64.decode(bytes);
    }

    @Test
    public void aes() {
        String secretKeyEncoded = generateAesKey();
        // 解码
        byte[] decode = decode(secretKeyEncoded);

        String data = "上山打老虎";
        log.info("加密前的内容 : {}", data);

        byte[] encrypt = SecureUtil.aes(decode).encrypt(data);
        log.info("加密后的内容 : {}", new String(encrypt));

        byte[] decrypt = SecureUtil.aes(decode).decrypt(encrypt);
        log.info("解密后的内容 : {}", new String(decrypt, StandardCharsets.UTF_8));
    }

    public String generateDesKey() {
        // 密钥生成  针对对称加密生成密钥
        byte[] secretKeyEncoded = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue()).getEncoded();
        //将秘钥转为Base64
        return encode(secretKeyEncoded);
    }

    @Test
    public void des() {
        String secretKeyEncoded = generateDesKey();
        // 解码
        byte[] decode = decode(secretKeyEncoded);

        String data = "宝塔镇河妖";
        log.info("加密前的内容 : {}", data);

        byte[] encrypt = SecureUtil.des(decode).encrypt(data);
        log.info("加密后的内容 : {}", new String(encrypt, StandardCharsets.UTF_8));

        byte[] decrypt = SecureUtil.des(decode).decrypt(encrypt);
        log.info("解密后的内容 : {}", new String(decrypt, StandardCharsets.UTF_8));
    }
}
