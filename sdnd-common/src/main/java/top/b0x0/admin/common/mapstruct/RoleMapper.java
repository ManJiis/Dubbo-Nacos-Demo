
package top.b0x0.admin.common.mapstruct;

import top.b0x0.admin.common.dto.RoleDto;
import top.b0x0.admin.common.pojo.system.SysRole;
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
