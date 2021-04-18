package top.b0x0.admin.common.util.properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 读取properties配置文件信息
 *
 * @author TANG
 * @date 2021-02-13
 */
public class CommonApplicationProperties {

    private static final Logger log = LoggerFactory.getLogger(CommonApplicationProperties.class);

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(CommonApplicationProperties.class.getClassLoader().getResourceAsStream("application.properties"));
            log.info("ActEnv --> : {}", PROPERTIES.getProperty("oss.profiles.active"));
        } catch (Exception e) {
            log.warn("Load Properties error : {}", e.getMessage());
        }
    }

    public static Properties getProperties() {
        return PROPERTIES;
    }
}