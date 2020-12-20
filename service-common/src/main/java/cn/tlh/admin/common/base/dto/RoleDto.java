
package cn.tlh.admin.common.base.dto;

import cn.tlh.admin.common.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Objects;

/**
 * @author TANG
 * @date 2020-11-23
 */
@Getter
@Setter
public class RoleDto extends BaseDTO implements Serializable {

    private Long roleId;

    private String name;

    private String dataScope;

    private Integer level;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(roleId, roleDto.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}
