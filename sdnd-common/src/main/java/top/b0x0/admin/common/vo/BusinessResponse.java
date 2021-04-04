package top.b0x0.admin.common.vo;


import top.b0x0.admin.common.util.enums.BusinessMsgEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一返回对象
 *
 * @author TANG
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessResponse {

    private Object data;
    private Integer code;
    private String message;

    /********************** 成功模板 **********************/
    public static BusinessResponse ok() {
        return new BusinessResponse("", 200, "操作成功！");
    }

    public static BusinessResponse ok(Integer code, String msg) {
        return new BusinessResponse("", code, msg);
    }

    public static BusinessResponse ok(Object data) {
        return new BusinessResponse(data, 200, "操作成功！");
    }

    public static BusinessResponse ok(Object data, Integer code, String msg) {
        return new BusinessResponse(data, code, msg);
    }

    /********************** 失败模板 **********************/
    public static BusinessResponse fail(String message) {
        return new BusinessResponse("", 400, message);
    }

    public static BusinessResponse fail(Integer code, String message) {
        return new BusinessResponse("", code, message);
    }

    public static BusinessResponse fail(Object data, Integer code, String msg) {
        return new BusinessResponse(data, code, msg);
    }

    /**
     * 使用自定义异常作为参数传递状态码和提示信息
     *
     * @param msgEnum BusinessMsgEnum
     */
    public static BusinessResponse fail(BusinessMsgEnum msgEnum) {
        return new BusinessResponse("", msgEnum.code(), msgEnum.msg());
    }

}
