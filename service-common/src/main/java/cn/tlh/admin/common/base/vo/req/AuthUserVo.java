package cn.tlh.admin.common.base.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;


/**
 * @author TANG
 */
@Getter
@Setter
@ToString
@ApiModel("登录请求对象")
public class AuthUserVo {

    @NotBlank(message = "登录账号不能为空")
    @ApiModelProperty("登录账号")
    private String loginAccount;

    @NotBlank(message = "登录密码不能为空")
    @ApiModelProperty("登录密码")
    private String password;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("动态码")
    private String secCode;

    private String uuid = "";
}