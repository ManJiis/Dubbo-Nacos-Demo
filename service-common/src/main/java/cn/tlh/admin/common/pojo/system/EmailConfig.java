package cn.tlh.admin.common.pojo.system;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 邮件配置类，数据存覆盖式存入数据存
 *
 * @author TANG
 * @date 2020-12-26
 */
@Data
//@Table(name = "tool_email_config")
public class EmailConfig implements Serializable {

    // @Id
    // @Column(name = "config_id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "邮件服务器SMTP地址")
    private String host;

    @ApiModelProperty(value = "邮件服务器 SMTP 端口")
    private String port;

    @ApiModelProperty(value = "发件者用户名")
    private String user;

    @ApiModelProperty(value = "密码")
    private String pass;

    @ApiModelProperty(value = "收件人")
    private String fromUser;
}
