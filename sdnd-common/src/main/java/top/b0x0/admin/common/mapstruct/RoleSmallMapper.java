
package top.b0x0.admin.common.mapstruct;

import top.b0x0.admin.common.dto.RoleSmallDto;
import top.b0x0.admin.common.pojo.system.SysRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * @author TANG
 * @date 2019-5-23
 */
@Mapper(unmappedSourcePolicy = IGNORE)
public interface RoleSmallMapper extends BaseMapper<RoleSmallDto, SysRole> {
    RoleSmallMapper MAPPER = Mappers.getMapper(RoleSmallMapper.class);
}
