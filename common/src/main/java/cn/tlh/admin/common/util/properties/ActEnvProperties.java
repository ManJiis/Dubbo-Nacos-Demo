package cn.tlh.admin.common.util.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 读取properties配置文件信息
 *
 * @author TANG
 * @description 读取properties配置文件信息
 * @date 20201-02-1323
 */
public class ActEnvProperties {

    private static final Logger log = LoggerFactory.getLogger(ActEnvProperties.class);

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(ActEnvProperties.class.getClassLoader().getResourceAsStream("ActEnv.properties"));
            log.info("ActEnv --> : {}", PROPERTIES.getProperty("ActEnv"));
        } catch (Exception e) {
            log.warn("Load Properties error : {}", e.getMessage());
        }
    }

    public static Properties getProperties() {
        return PROPERTIES;
    }
}