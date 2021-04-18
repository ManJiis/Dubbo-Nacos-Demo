package top.b0x0.admin.common.util.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author TANG
 * @date 2020-05-18
 **/
@Data
@Component
@ConfigurationProperties(prefix = "rsa")
public class RsaProperties {
    /**
     * 密码加密传输，前端公钥加密，后端私钥解密
     */
    public static String privateKey;
    public static String publicKey;

}