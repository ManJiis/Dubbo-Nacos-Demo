package cn.tlh.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @Auther: yilin
 * @Date: 2019/4/29 11:15
 * @Description: 服务商添加user
 */
@Data
public class ServicerUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("登录账户")
    @NotBlank(message = "登录账户不能为空")
    private String childPhone;

    @ApiModelProperty("登录密码")
    private Integer childPassword;

    @ApiModelProperty("操作  1:启用  0: 初始化  2:停用")
    private Integer status;

}
