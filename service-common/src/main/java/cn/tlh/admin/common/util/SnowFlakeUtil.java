package cn.tlh.admin.common.util;

import cn.hutool.core.util.IdUtil;

/**
 * 雪花算法
 * @author TANG
 * @date 2020/12/6
 */
public class SnowFlakeUtil {

    public static Long createSnowflakeId() {
        return IdUtil.createSnowflake(1, 1).nextId();
    }
}