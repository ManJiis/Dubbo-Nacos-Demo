package top.b0x0.admin.common.mapstruct;

import top.b0x0.admin.common.dto.UserDto;
import top.b0x0.admin.common.pojo.system.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author TANG
 * @date 2020-11-23
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDto, SysUser> {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
}
