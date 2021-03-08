
package cn.tlh.admin.common.base.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author TANG
 * @date 2020-11-23
 */
@Data
public class RoleSmallDto implements Serializable {

    private static final long serialVersionUID = -5065232360300065416L;
    private Long id;

    private String name;

    private Integer level;

    private String dataScope;
}
