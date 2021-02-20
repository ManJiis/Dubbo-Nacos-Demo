package cn.tlh.admin.consumer.test.hutool.crypto;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.tlh.admin.DemoApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 非对称加密
 * 加解密使用的是不同的秘钥
 * 基于“非对称密钥”的加密算法主要有RSA、Elgamal、背包算法、Rabin、D-H、ECC（椭圆曲线加密算法）。
 * <p>
 * 使用最广泛的是RSA算法，Elgamal是另一种常用的非对称加密算法。
 *
 * @author TANG
 * @date 2021-02-19
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class AsymmetricTest {
    private final static Logger log = LoggerFactory.getLogger(SymmetricTest.class);

    //    密钥生成
    //        - SecureUtil.generateKey 针对对称加密生成密钥
    //        - SecureUtil.generateKeyPair 生成密钥对（用于非对称加密）
    //        - SecureUtil.generateSignature 生成签名（用于非对称加密）
    //    非对称加密
    //        - SecureUtil.rsa
    //        - SecureUtil.dsa

    @Test
    public void rsaTest() {
        RSA rsa = new RSA();

        //获得私钥
        rsa.getPrivateKey();
        rsa.getPrivateKeyBase64();
        //获得公钥
        rsa.getPublicKey();
        rsa.getPublicKeyBase64();

        //公钥加密，私钥解密
        byte[] encrypt = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);

        //Junit单元测试
        //Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

        //私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes("我是一段测试aaaa", CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);

        //Junit单元测试
        //Assert.assertEquals("我是一段测试aaaa", StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));

    }

    public String encode(byte[] bytes) {
        return Base64.encode(bytes);
    }

    public byte[] decode(String bytes) {
        return Base64.decode(bytes);
    }

    @Test
    public void generateRsaLog() {
        //  RSA/ECB/PKCS1Padding
        KeyPair rsa = SecureUtil.generateKeyPair("RSA");
        PrivateKey aPrivate = rsa.getPrivate();
        PublicKey aPublic = rsa.getPublic();

        // Base64
        String privateKey = encode(aPrivate.getEncoded());
        String publicKey = encode(aPublic.getEncoded());
        log.info("===========私钥: {}", privateKey);
        log.info("===========公钥: {}", publicKey);
    }

    /**
     * 生成rsa私钥秘钥
     *
     * @return MyKeyPair
     */
    public MyKeyPair generateRsaKeyPair() {
        //  RSA/ECB/PKCS1Padding
        KeyPair rsa = SecureUtil.generateKeyPair("RSA");
        PrivateKey aPrivate = rsa.getPrivate();
        PublicKey aPublic = rsa.getPublic();
        return new MyKeyPair(aPublic.getEncoded(), aPrivate.getEncoded());
    }

    @Test
    public void rsa() {
        MyKeyPair myKeyPair = generateRsaKeyPair();
        // 创建rsa对象
        RSA rsa = SecureUtil.rsa(myKeyPair.getPrivateKey(), myKeyPair.getPublicKey());

        Map<String, String> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", "23");
        log.info("加密前的内容 : {}", map.toString());

        log.info("--------------------- 私钥加密 公钥解密--------------------------------------");
        // 私钥加密
        String encryptBcd = rsa.encryptBcd(map.toString(), KeyType.PrivateKey);
        log.info("加密后的内容 : {}", encryptBcd);

        // 公钥解密
        byte[] bytes = rsa.decryptFromBcd(encryptBcd, KeyType.PublicKey);
        log.info("解密后的内容 : {}", new String(bytes, StandardCharsets.UTF_8));

        log.info("--------------------- 公钥加密 私钥解密--------------------------------------");

        // 公钥加密
        String encryptBcd2 = rsa.encryptBcd(map.toString(), KeyType.PublicKey);
        log.info("加密后的内容 : {}", encryptBcd2);

        // 私钥解密
        byte[] bytes2 = rsa.decryptFromBcd(encryptBcd2, KeyType.PrivateKey);
        log.info("解密后的内容 : {}", new String(bytes2, StandardCharsets.UTF_8));
    }

    @Test
    public void test() {
        String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIL7pbQ+5KKGYRhw7jE31hmA"
                + "f8Q60ybd+xZuRmuO5kOFBRqXGxKTQ9TfQI+aMW+0lw/kibKzaD/EKV91107xE384qOy6IcuBfaR5lv39OcoqNZ"
                + "5l+Dah5ABGnVkBP9fKOFhPgghBknTRo0/rZFGI6Q1UHXb+4atP++LNFlDymJcPAgMBAAECgYBammGb1alndta"
                + "xBmTtLLdveoBmp14p04D8mhkiC33iFKBcLUvvxGg2Vpuc+cbagyu/NZG+R/WDrlgEDUp6861M5BeFN0L9O4hz"
                + "GAEn8xyTE96f8sh4VlRmBOvVdwZqRO+ilkOM96+KL88A9RKdp8V2tna7TM6oI3LHDyf/JBoXaQJBAMcVN7fKlYP"
                + "Skzfh/yZzW2fmC0ZNg/qaW8Oa/wfDxlWjgnS0p/EKWZ8BxjR/d199L3i/KMaGdfpaWbYZLvYENqUCQQCobjsuCW"
                + "nlZhcWajjzpsSuy8/bICVEpUax1fUZ58Mq69CQXfaZemD9Ar4omzuEAAs2/uee3kt3AvCBaeq05NyjAkBme8SwB0iK"
                + "kLcaeGuJlq7CQIkjSrobIqUEf+CzVZPe+AorG+isS+Cw2w/2bHu+G0p5xSYvdH59P0+ZT0N+f9LFAkA6v3Ae56OrI"
                + "wfMhrJksfeKbIaMjNLS9b8JynIaXg9iCiyOHmgkMl5gAbPoH/ULXqSKwzBw5mJ2GW1gBlyaSfV3AkA/RJC+adIjsRGg"
                + "JOkiRjSmPpGv3FOhl9fsBPjupZBEIuoMWOC8GXK/73DHxwmfNmN7C9+sIi4RBcjEeQ5F5FHZ";

        RSA rsa = new RSA(PRIVATE_KEY, null);

        String a = "2707F9FD4288CEF302C972058712F24A5F3EC62C5A14AD2FC59DAB93503AA0FA17113A020EE4EA35EB53F"
                + "75F36564BA1DABAA20F3B90FD39315C30E68FE8A1803B36C29029B23EB612C06ACF3A34BE815074F5EB5AA3A"
                + "C0C8832EC42DA725B4E1C38EF4EA1B85904F8B10B2D62EA782B813229F9090E6F7394E42E6F44494BB8";

        // 将十六进制字符数组转换为字节数组
        byte[] aByte = HexUtil.decodeHex(a);
        // rsa解密
        byte[] decrypt = rsa.decrypt(aByte, KeyType.PrivateKey);

        //Junit单元测试
        Assert.assertEquals("虎头闯杭州,多抬头看天,切勿只管种地", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));
//        Assert.assertEquals("虎头闯杭州,多抬头看天", StrUtil.str(decrypt, CharsetUtil.CHARSET_UTF_8));

    }

    @Test
    public void test2() {
        String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAJIROYKRA2G9fkjGPedQ3ViglXOV8hkM0BAhuz5mCyL5+YxcS6a4Mle8WfLkA2ukMRKt4FOkOpCEFpWC5OYT1ROv+sCeNQxEWa3ugGy3h1q2BR7/xzqfSdpp50zZFIRF2JMn/eMFDZaMVZVC0WNbb+LcfF+NDga3FEEOZVGbMZCLAgMBAAECgYEAgp6gnR/stETuX0nYJbjsl8rSmxBl9Py+Ow7PENfT8a00+c23YRmq+0ZV8ZzxahrkP136ExHzxTQ5te3dn9nwWlbU0yPm/nxhC0b2wPJfrLF020EjWJHs5ZCOm1W9uFsf4BTW/yh4GryJhWIfv9RHT08V1W+ef35Vh8aeHL3w9QECQQDBYm1Hy1hnuhJEwb45gNkhiKxvP5k3gaa9y1ALF05DKOwEOg3XUJw+3oNOKZJ3cSpBmmIiL6NB088qKw0L+2lLAkEAwVyxDrakPDxUhfX3ITeHmCqD7HNa0/US4Otnx3nXu7qV4eN1SpI8BeVrNRSyX+NpyMl9tCqmz7G6ZLr9yU8twQJBAK/yydMEpI7bsTt1u4m+PdBQ9fLSPqlOAVO15EHzFnXsibDe0TESWtaoxXccQ/MAt52wxZCtHzEJcfe/68L3IDkCQQC/Q6q3/R2zn5GT+Nr89mqfr95Ss0AyzQZhiTlcbT9iKIw9prrMip3ozygel1xh0RdAT16SKmZap1jSbsy+ph2BAkBGjCIOddRKelT9qayx61zsWGnsD60heMlUVjPg9/lEq+1InD2VJtayVYO7EdAXX5UY12xlDb2NWRnx+D9SYXtk";
        String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSETmCkQNhvX5Ixj3nUN1YoJVzlfIZDNAQIbs+Zgsi+fmMXEumuDJXvFny5ANrpDESreBTpDqQhBaVguTmE9UTr/rAnjUMRFmt7oBst4datgUe/8c6n0naaedM2RSERdiTJ/3jBQ2WjFWVQtFjW2/i3HxfjQ4GtxRBDmVRmzGQiwIDAQAB";

        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);

    }
}
