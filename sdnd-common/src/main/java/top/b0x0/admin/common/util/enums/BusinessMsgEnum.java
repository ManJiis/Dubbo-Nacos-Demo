package top.b0x0.admin.common.util.enums;

/**
 * 业务异常提示信息枚举类
 *
 * @author TANG
 */
public enum BusinessMsgEnum {
    /**
     * 参数异常
     */
    PARMETER_EXCEPTION(102, "参数异常!"),
    /**
     * 等待超时
     */
    SERVICE_TIME_OUT(103, "服务调用超时！"),
    /**
     * 参数过大
     */
    PARMETER_BIG_EXCEPTION(102, "输入的图片数量不能超过50张!"),
    /**
     * 500 : 发生异常
     */
    UNEXPECTED_EXCEPTION(500, "服务器开小差了,请稍后重试!");

    /**
     * 消息码
     */
    private final Integer code;
    /**
     * 消息内容
     */
    private final String msg;

    private BusinessMsgEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }

}
