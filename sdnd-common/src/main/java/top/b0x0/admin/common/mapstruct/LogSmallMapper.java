package top.b0x0.admin.common.mapstruct;

import top.b0x0.admin.common.dto.LogSmallDTO;
import top.b0x0.admin.common.pojo.system.SysLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * @author TANG
 * @date 2019-5-22
 */
@Mapper(unmappedSourcePolicy = IGNORE)
public interface LogSmallMapper extends BaseMapper<LogSmallDTO, SysLog> {
    LogSmallMapper MAPPER = Mappers.getMapper(LogSmallMapper.class);
}