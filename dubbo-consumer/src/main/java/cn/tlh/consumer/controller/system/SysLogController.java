package cn.tlh.consumer.controller.system;

import cn.tlh.common.pojo.system.SysLog;
import cn.tlh.service.system.LogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统日志(SysLog)表控制层
 *
 * @author makejava
 * @since 2020-12-17 09:52:01
 */
@RestController
@RequestMapping("sysLog")
public class SysLogController {
    /**
     * 服务对象
     */
    @Resource
    private LogService logService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysLog selectOne(Long id) {
        return this.logService.queryById(id);
    }

}