package top.b0x0.admin.common.mapstruct;

import top.b0x0.admin.common.dto.LogErrorDTO;
import top.b0x0.admin.common.pojo.system.SysLog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static org.mapstruct.ReportingPolicy.IGNORE;

/**
 * @author TANG
 * @date 2020-12-18
 */
@Mapper(unmappedSourcePolicy = IGNORE)
public interface LogErrorMapper extends BaseMapper<LogErrorDTO, SysLog> {
    LogErrorMapper MAPPER = Mappers.getMapper(LogErrorMapper.class);
}