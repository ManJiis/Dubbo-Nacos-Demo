package cn.tlh.admin.common.base.mapstruct;

import cn.tlh.admin.common.base.dto.LogErrorDTO;
import cn.tlh.admin.common.pojo.system.SysLog;
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