package cn.tlh.admin.common.base.vo.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


/**
 * @author TANG
 */
@Getter
@Setter
public class AuthUserVo {

    @NotBlank(message = "登录账号不能为空")
    private String username;

    @NotBlank(message = "登录密码不能为空")
    private String password;

    private String code;

    private String uuid = "";
}