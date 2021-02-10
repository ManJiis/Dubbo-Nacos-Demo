package cn.tlh.admin.common.base.mapstruct;

import cn.tlh.admin.common.base.dto.LogSmallDTO;
import cn.tlh.admin.common.pojo.system.SysLog;
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