package top.b0x0.admin.consumer.test;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.b0x0.admin.common.util.RsaUtils;
import top.b0x0.admin.common.util.constants.CommonConstants;
import top.b0x0.admin.common.util.properties.RsaProperties;
import top.b0x0.admin.consumer.shiro.ShiroUtils;

/**
 * @author TANG
 * @since 2021/04/05
 */
@SpringBootTest
public class RsaTest {
    private String public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7B8JPOvj53EH/r0MHqW0uo9Z7MBXPR38hrbOJNyDLWxLpN7wiE92SnbJ95ykYyiqZnOfpHNh0Di3dclwED6TqcH2EICC7C2Ba6Z/X+BnSpy9PHXSTO6wE7Ak4jDMRpmIERMOclUizIjpckUM9oJ9jKoGE0yTBquOraiqKqFeUdQIDAQAB";
    private String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALsHwk86+PncQf+vQwepbS6j1nswFc9HfyGts4k3IMtbEuk3vCIT3ZKdsn3nKRjKKpmc5+kc2HQOLd1yXAQPpOpwfYQgILsLYFrpn9f4GdKnL08ddJM7rATsCTiMMxGmYgREw5yVSLMiOlyRQz2gn2MqgYTTJMGq46tqKoqoV5R1AgMBAAECgYEAj/Rx59/hQRf6KJ2yfxQ2OF7cuCaTst46CScUeDnZYQxxatZ+VNBri+0bNHMXG8CHt4KgsbmFFRbQ0JC0KGAOMx7tZiKBIjHbqc9F/n4e2QPLE4WTfhGV0uqIG7yTRcs3jVkttNHzJlMVZrvS5i0VxLhtmmD4bp2bj6HQ/HgODmECQQDpC1m8IYwcp1f2JNms8+iSfc/n0e6YemamHMZgmRKYH0SjGIRtnPAjptnK1h2jfLREoq00cMjNRo6lXQpNbdh5AkEAzXQUHJhDXVlal5peTrwDT4cbznrm/kAqdDZ/4S0VjQSkefNp4pRelWxBoXyo3h9J+tgNh3SKlwyuRKJ5SlxU3QJBAK1j5ZMlPrM/CyI3jsGxBDSiaSLpPmWyhFdJh4vtuDj44r+aiUVpA/7ysI7LOmgea525j7c5xx18Rrvger7bYLkCQBlyOA92mFlWeM7wEqAjzzEpZbtEI5n37oooOdx0zgeCPLDSAhpLEA/nsZVDsFKag3orCjntqECfFHJxwR4Bo40CQB237wZA5ovbhtNwZFWTxuaizHMtJRdZvkeHA69HlApR7OnovA2L3Fz2ks2WPLdtFx9/lIGyBFYr8z5rHbLPNhI=";

    @Test
    public void test2() {
        String salt = ShiroUtils.generateSalt();
        System.out.println("salt = " + salt);
        System.out.println("ShiroUtils.sha256 = " + ShiroUtils.sha256(CommonConstants.DEFAULT_PASSWORD, salt));
        //salt = pBUqvDltJh58ZbfFPKPq
        //ShiroUtils.sha256 = 269559d07d31d06df604a1b7028ec38f5a19573adf917edcd3dec4a5e63af3f1
    }

    @Test
    public void test() throws Exception {
        String pubStr = Base64.encodeBase64String(public_key.getBytes());
        String password = RsaUtils.encryptByPublicKey(pubStr, "123456");
        System.out.println("enPassword = " + password);
        String priStr = Base64.encodeBase64String(private_key.getBytes());
        System.out.println("dePassword = " + RsaUtils.decryptByPrivateKey(priStr, password));
    }

    @Test
    public void test3() throws Exception {
        String password = RsaUtils.encryptByPublicKey(public_key, "123456");
        System.out.println("enPassword = " + password);
        System.out.println("dePassword = " + RsaUtils.decryptByPrivateKey(private_key, password));
    }

    @Test
    public void test4() throws Exception {
        System.out.println("RsaProperties.publicKey = " + RsaProperties.publicKey);
        System.out.println("RsaProperties.privateKey = " + RsaProperties.privateKey);
        String password = RsaUtils.encryptByPublicKey(RsaProperties.publicKey, "123456");
        System.out.println("enPassword = " + password);
        System.out.println("dePassword = " + RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, password));
    }
}
