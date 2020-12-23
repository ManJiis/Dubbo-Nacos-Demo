package cn.tlh.admin.dao;

import cn.tlh.admin.common.base.vo.req.LogReqVo;
import cn.tlh.admin.common.pojo.system.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统日志(SysLog)表数据库访问层
 *
 * @author TANG
 * @since 2020-12-17 09:52:01
 */
public interface SysLogDao {

    /**
     * 根据日志类型删除日志
     *
     * @param logType 日志类型 INFO/ERROR
     * @return 实例对象
     */
    long delAllLogType(String logType);

    /**
     * 通过ID查询单条数据
     *
     * @param logId 主键
     * @return 实例对象
     */
    SysLog queryById(Long logId);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param logReqVo 实例对象
     * @return 对象列表
     */
    List<SysLog> selectList(LogReqVo logReqVo);

    /**
     * 新增数据
     *
     * @param sysLog 实例对象
     * @return 影响行数
     */
    int insert(SysLog sysLog);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysLog> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysLog> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysLog> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SysLog> entities);

    /**
     * 修改数据
     *
     * @param sysLog 实例对象
     * @return 影响行数
     */
    int update(SysLog sysLog);

    /**
     * 通过主键删除数据
     *
     * @param logId 主键
     * @return 影响行数
     */
    int deleteById(Long logId);

}