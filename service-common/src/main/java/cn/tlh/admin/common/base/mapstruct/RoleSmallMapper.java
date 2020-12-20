
package cn.tlh.admin.common.base.mapstruct;

import cn.tlh.admin.common.pojo.system.SysRole;
import cn.tlh.admin.common.base.BaseMapper;

import cn.tlh.admin.common.base.dto.RoleSmallDto;
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
