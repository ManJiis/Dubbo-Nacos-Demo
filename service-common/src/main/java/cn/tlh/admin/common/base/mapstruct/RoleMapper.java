
package cn.tlh.admin.common.base.mapstruct;

import cn.tlh.admin.common.pojo.system.SysRole;
import cn.tlh.admin.common.base.BaseMapper;

import cn.tlh.admin.common.base.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * @author TANG
 * @date 2020-11-23
 */
@Mapper(unmappedSourcePolicy = IGNORE)
public interface RoleMapper extends BaseMapper<RoleDto, SysRole> {
    RoleMapper MAPPER = Mappers.getMapper(RoleMapper.class);

}
