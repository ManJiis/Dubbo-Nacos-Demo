package top.b0x0.admin.common.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.b0x0.admin.common.util.enums.BusinessMsgEnum;

/**
 * 统一返回对象
 *
 * @author TANG
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BusinessResponse{

    private Object data;
    private Integer code;
    private String message;

    /********************** 成功模板 **********************/
    public static  BusinessResponse ok() {
        BusinessResponse response = new BusinessResponse();
        response.setCode(200).setMessage("操作成功");
        return response;
    }

    public static  BusinessResponse ok(Integer code, String message) {
        BusinessResponse response = new BusinessResponse();
        response.setCode(code).setMessage(message);
        return response;
    }

    public static  BusinessResponse ok(Object data) {
        return new BusinessResponse(data, 200, "操作成功！");
    }

    public static  BusinessResponse ok(Object data, Integer code, String message) {
        return new BusinessResponse(data, code, message);
    }

    /********************** 失败模板 **********************/
    public static  BusinessResponse fail(String message) {
        BusinessResponse response = new BusinessResponse();
        response.setCode(400).setMessage(message);
        return response;
    }

    public static  BusinessResponse fail(Integer code, String message) {
        BusinessResponse response = new BusinessResponse();
        response.setCode(code).setMessage(message);
        return response;
    }

    public static  BusinessResponse fail(Object data, Integer code, String msg) {
        return new BusinessResponse(data, code, msg);
    }

    /**
     * 使用自定义异常作为参数传递状态码和提示信息
     *
     * @param msgEnum BusinessMsgEnum
     */
    public static  BusinessResponse fail(BusinessMsgEnum msgEnum) {
        BusinessResponse response = new BusinessResponse();
        response.setCode(msgEnum.getCode()).setMessage(msgEnum.getMessage());
        return response;
    }

}
