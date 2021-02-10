package cn.tlh.admin.consumer.shiro;

import cn.tlh.admin.common.pojo.system.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;

/**
 * @author TANG
 * @description Shiro工具类
 * @date 2020-12-21
 */
public class ShiroUtils {

    /**
     * 加密算法
     */
    public final static String hashAlgorithmName = "SHA-256";
    /**
     * 循环次数
     */
    public final static int hashIterations = 16;

    /**
     * 生成随机盐
     *
     * @param length 字节长度，一个字节2位16进制数表示
     * @return string  salt
     */
    public static String genRandomSalt(int length) {
        return new SecureRandomNumberGenerator().nextBytes(length).toHex();
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
    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
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

    public static void main(String[] args) {
        String randomSalt = genRandomSalt(6);
        String s = ShiroUtils.sha256("admin", randomSalt);
        String s1 = ShiroUtils.sha256("wang", randomSalt);
        String s2 = ShiroUtils.sha256("guest", randomSalt);
        System.out.println("s = " + s);
        System.out.println("s.length() = " + s.length());
        System.out.println("s1 = " + s1);
        System.out.println("s1.length() = " + s1.length());
        System.out.println("s2 = " + s2);
        System.out.println("s2.length() = " + s2.length());
    }
}