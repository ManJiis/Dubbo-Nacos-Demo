package cn.tlh.admin.common.pojo.system;

import cn.tlh.admin.common.util.enums.DataScopeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 角色表(SysRole)实体类
 *
 * @author TANG
 * @since 2020-12-17
 */
@Getter
@Setter
@Table(name = "sys_role")
public class SysRole implements Serializable {
    private static final long serialVersionUID = 182136052311251942L;
    /**
     * ID
     */
    // @Id
    // @Column(name = "role_id")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long roleId;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;
    /**
     * 角色级别 数值越小，级别越大
     */
    @ApiModelProperty(value = "级别，数值越小，级别越大")
    private Integer level;
    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;
    /**
     * 数据权限
     */
    @ApiModelProperty(value = "数据权限，全部 、 本级 、 自定义")
    private String dataScope = DataScopeEnum.THIS_LEVEL.getValue();

    @ApiModelProperty(value = "菜单")
    private String menu;
    @ApiModelProperty(value = "权限")
    private String perm;

    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysRole role = (SysRole) o;
        return Objects.equals(roleId, role.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}