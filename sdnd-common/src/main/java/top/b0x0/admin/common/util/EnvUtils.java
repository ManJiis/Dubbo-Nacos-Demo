package top.b0x0.admin.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author TANG
 * @since 2021/05/26
 */
public class EnvUtils {
    private static final Logger log = LoggerFactory.getLogger(EnvUtils.class);

    private static final String ossActive;

    static {
        // 获取JVM参数 -Doss.active=prod
        ossActive = System.getProperty("oss.active");
        log.info("oss.active = [{}]", ossActive);
        String[] ossArr = {"dev", "test", "prod"};
        boolean isContains = Arrays.asList(ossArr).contains(ossActive);
        if (isContains) {
            System.out.println("do something..... ");
        } else {
            System.out.println("读取配置文件... ");
        }
    }

    public static String getOssActive() {
        return ossActive;
    }
}
