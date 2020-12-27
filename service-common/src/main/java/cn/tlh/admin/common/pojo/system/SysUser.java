package cn.tlh.admin.common.pojo.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 系统用户(SysUser)实体类
 *
 * @author TANG
 * @since 2020-12-17 09:51:55
 */
@Getter
@Setter
@Table(name = "sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 649715419116774602L;
    /**
     * ID
     */
    // @Id
    // @Column(name = "user_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long userId;
    /**
     * 部门名称
     */
    private Long deptId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名称")
    private String username;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;
    /**
     * 性别
     */
    @ApiModelProperty(value = "用户性别")
    private String gender;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "电话号码")
    private String phone;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;
    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像存储的路径", hidden = true)
    private String avatarName;
    /**
     * 头像真实路径
     */
    @ApiModelProperty(value = "头像真实名称", hidden = true)
    private String avatarPath;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 是否为admin账号
     */
    @ApiModelProperty(value = "是否为admin账号", hidden = true)
    private Boolean isAdmin;
    /**
     * 状态：1启用、0禁用
     */
    @ApiModelProperty(value = "是否启用")
    private Integer enabled;

    private String salt;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createBy;
    /**
     * 更新着
     */
    @ApiModelProperty(value = "更新人", hidden = true)
    private String updateBy;
    /**
     * 修改密码的时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    // @Column(name = "pwd_reset_time")
    @ApiModelProperty(value = "最后修改密码的时间", hidden = true)
    private LocalDateTime pwdResetTime;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
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