package top.b0x0.admin.consumer.shiro;

import top.b0x0.admin.common.pojo.system.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;

import java.security.SecureRandom;

/**
 * Shiro工具类
 *
 * @author TANG
 * @date 2020-12-21
 */
public class ShiroUtils {

    /**
     * 加密算法
     */
    public final static String HASH_ALGORITHM_NAME = "SHA-256";
    /**
     * 循环次数
     */
    public final static int HASH_ITERATIONS = 16;

    private static final String ORGIN_STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    /**
     * 生成随机盐
     *
     * @param length 字节长度，一个字节2位  16进制数表示
     * @return string  salt
     */
    public static String getRandomSalt(Integer length) {
        return new SecureRandomNumberGenerator().nextBytes(length == null ? 6 : length).toHex();
    }

    public static String getRandomSalt() {
        return new SecureRandomNumberGenerator().nextBytes(6).toHex();
    }

    public static String generateSalt() {
        StringBuilder builder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 20; i++) {
            builder.append(ORGIN_STR.charAt(secureRandom.nextInt(ORGIN_STR.length())));
        }
        return builder.toString();
    }

    /**
     * 获取当前 Subject
     *
     * @return Subject
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 加盐加密
     *
     * @param password /
     * @param salt     /
     * @return /
     */
    public static String sha256Pass(String password, String salt) {
        return new SimpleHash(HASH_ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toString();
    }

    /**
     * 获取当前登录用户
     *
     * @return /
     */
    public static SysUser getUserEntity() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登录用户id
     *
     * @return /
     */
    public static Long getUserId() {
        return getUserEntity().getUserId();
    }

//    public static void main(String[] args) {
//        String randomSalt = getRandomSalt(6);
//        String pass1 = ShiroUtils.sha256Pass("admin", randomSalt);
//        String pass2 = ShiroUtils.sha256Pass("guest", randomSalt);
//        System.out.println("salt = " + randomSalt + " pass1 = " + pass1 + " pass1.length() = " + pass1.length());
//        System.out.println("salt = " + randomSalt + " pass2 = " + pass2 + " pass2.length() = " + pass2.length());
//        String randomSalt1 = getRandomSalt();
//        String pass3 = ShiroUtils.sha256Pass("admin", randomSalt1);
//        System.out.println("salt = " + randomSalt1 + " pass3 = " + pass1 + " pass3.length() = " + pass3.length());
//
//    }
}