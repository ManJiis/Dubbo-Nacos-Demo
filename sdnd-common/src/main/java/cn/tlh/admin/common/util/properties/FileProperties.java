
package cn.tlh.admin.common.util.properties;

import cn.tlh.admin.common.util.constants.ElAdminConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author TANG
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "file")
public class FileProperties {

    /**
     * 文件大小限制
     */
    private Long maxSize;

    /**
     * 头像大小限制
     */
    private Long avatarMaxSize;

    private ElPath mac;

    private ElPath linux;

    private ElPath windows;

    //# 文件存储路径
    //file:
    //  mac:
    //    path: ~/file/
    //    avatar: ~/avatar/
    //  linux:
    //    path: /home/eladmin/file/
    //    avatar: /home/eladmin/avatar/
    //  windows:
    //    path: C:\eladmin\file\
    //    avatar: C:\eladmin\avatar\
    //  # 文件大小 /M
    //  maxSize: 100
    //  avatarMaxSize: 5

    public ElPath getPath() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith(ElAdminConstant.WIN)) {
            return windows;
        } else if (os.toLowerCase().startsWith(ElAdminConstant.MAC)) {
            return mac;
        }
        return linux;
    }

    @Data
    public static class ElPath {

        private String path;

        private String avatar;
    }
}
