package top.b0x0.admin.common.util.id;

import cn.hutool.core.util.IdUtil;

/**
 * id生成工具类
 *
 * @author TANG
 * @date 2020-12-06
 */
public class IdUtils extends IdUtil {

    public static Long snowflakeId() {
        return IdUtil.getSnowflake(1, 1).nextId();
    }

}