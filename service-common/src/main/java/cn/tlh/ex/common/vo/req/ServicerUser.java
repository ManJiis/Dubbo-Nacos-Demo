package cn.tlh.ex.common.vo.req;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: yilin
 * @Date: 2019/4/29 11:15
 * @Description: 服务商添加user
 */

public class ServicerUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("登录账户")
    @NotBlank(message = "登录账户不能为空")
    private String childPhone;

    @ApiModelProperty("登录密码")
    private Integer childPassword;

    @ApiModelProperty("OTP验证  通证ID")
    @NotBlank(message = "通证ID不能为空")
    private String otpId;

    @ApiModelProperty("操作  1:启用  0: 初始化  2:停用")
    private Integer status;

    public String getChildPhone() {
        return childPhone;
    }

    public void setChildPhone(String childPhone) {
        this.childPhone = childPhone;
    }

    public Integer getChildPassword() {
        return childPassword;
    }

    public void setChildPassword(Integer childPassword) {
        this.childPassword = childPassword;
    }

    public String getOtpId() {
        return otpId;
    }

    public void setOtpId(String otpId) {
        this.otpId = otpId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
