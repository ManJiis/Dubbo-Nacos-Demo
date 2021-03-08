package cn.tlh.admin.common.pojo.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 系统用户(SysUser)实体类
 *
 * @author TANG
 * @since 2020-12-17 09:51:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 649715419116774602L;

    @TableId(type = IdType.INPUT)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long userId;
    /**
     * 部门名称
     */
    private Long deptId;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户性别")
    private String gender;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像真实名称", hidden = true)
    private String avatarName;

    @ApiModelProperty(value = "头像真实路径", hidden = true)
    private String avatarPath;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "是否为admin账号", hidden = true)
    private Boolean isAdmin;

    @ApiModelProperty(value = "是否启用 1启用、0禁用")
    private Integer enabled;

    @ApiModelProperty(value = "盐", hidden = true)
    private String salt;

    @ApiModelProperty(value = "创建人", hidden = true)
    private String createBy;

    @ApiModelProperty(value = "更新人", hidden = true)
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // @Column(name = "pwd_reset_time")
    @ApiModelProperty(value = "最后修改密码的时间", hidden = true)
    private LocalDateTime pwdResetTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间", hidden = true)
    private LocalDateTime updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysUser user = (SysUser) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username);
    }

}