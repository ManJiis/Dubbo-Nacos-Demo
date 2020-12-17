package cn.tlh.consumer.controller.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * (SysUsersJobs)表控制层
 *
 * @author makejava
 * @since 2020-12-17 09:51:59
 */
@RestController
@RequestMapping("sysUsersJobs")
public class SysUsersJobsController {
    /**
     * 服务对象
     */
    @Resource
    private SysUsersJobsService sysUsersJobsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysUsersJobs selectOne(Long id) {
        return this.sysUsersJobsService.queryById(id);
    }

}