package cn.tlh.admin.common.util.constants;

import java.util.regex.Pattern;

/**
 * @author TANG
 * @description: 常量类
 * @date: 2020-12-05
 */
public class Constants {

    /**
     * 手机号
     */
    public static Pattern PHONE_REX = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
    /**
     * 邮箱 只允许英文字母、数字、下划线、英文句号、以及中划线组成
     */
    public static Pattern EMAIL_REX = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    /**
     * 验证码类型
     */
    public static final String CALCULATE = "calculate";
    public static final String SMS = "sms";
    public static final String DYNAMIC = "dynamic";

    /**
     * 超级管理员ID
     */
    public static final String[] ADMINS = new String[]{"0755-0001", "0755-0002"};
    /**
     * 账号默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 用户状态  0：禁用   1：正常  3:冻结
     */
    public static final int SYS_USER_STATUS_PROHIBIT = 0;
    public static final int SYS_USER_STATUS_NORMAL = 1;
    public static final int SYS_USER_STATUS_FROZEN = 2;

    /**
     * 发送状态： 0 发送中 1 成功 -1 失败
     */
    public static final String ORDER_SENDING = "0";
    public static final String ORDER_SEND_SUCCESS = "1";
    public static final String ORDER_SEND_FAILURE = "-1";

    /**
     * exception
     */
    public static final String BAD_REQUEST_MSG = "客户端请求参数错误";
}
