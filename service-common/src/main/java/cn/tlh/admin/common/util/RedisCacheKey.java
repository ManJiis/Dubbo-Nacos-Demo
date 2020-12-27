package cn.tlh.admin.common.util;

/**
 * @author TANG
 * @date: 2020-6-11
 * @description: 关于缓存的Key集合
 */
public class RedisCacheKey {

    /**
     * 计算验证码
     * 短信验证码
     * 动态码(二次验证码)
     */
    public static final String CALCULATE_VERIFICATION_CODE = "verification_code:calculate:%s";
    public static final String SMS_VERIFICATION_CODE = "verification_code:sms:%s";
    public static final String DYNAMIC_VERIFICATION_CODE = "verification_code:dynamic:%s";

    /**
     * 内置 用户、岗位、应用、菜单、角色 相关key
     */
    public static final String USER_MODIFY_TIME_KEY = "user:modify:time:key:";
    public static final String APP_MODIFY_TIME_KEY = "app:modify:time:key:";
    public static final String JOB_MODIFY_TIME_KEY = "job:modify:time:key:";
    public static final String MENU_MODIFY_TIME_KEY = "menu:modify:time:key:";
    public static final String ROLE_MODIFY_TIME_KEY = "role:modify:time:key:";
    public static final String DEPT_MODIFY_TIME_KEY = "dept:modify:time:key:";

    /**
     * 用户
     */
    public static final String USER_ID = "user::id:";
    public static final String USER_NAME = "user::username:";
    /**
     * 数据
     */
    public static final String DATE_USER = "data::user:";
    /**
     * 菜单
     */
    public static final String MENU_USER = "menu::user:";
    /**
     * 角色授权
     */
    public static final String ROLE_AUTH = "role::auth:";
    /**
     * 角色信息
     */
    public static final String ROLE_ID = "role::id:";
}
