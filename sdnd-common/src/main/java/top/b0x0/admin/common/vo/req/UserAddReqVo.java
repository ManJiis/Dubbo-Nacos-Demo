package top.b0x0.admin.common.vo.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author TANG
 * @description 新增用户请求对象
 * @date 2020-12-30
 */
@Data
public class UserAddReqVo {
    @JsonProperty("deptId")
    private Integer deptId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("enabled")
    private Integer enabled;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("nickName")
    private String nickName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("username")
    private String username;
}
