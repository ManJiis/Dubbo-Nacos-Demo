package cn.tlh.consumer.controller.system;

import cn.tlh.common.pojo.system.SysDept;
import cn.tlh.service.system.DeptService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 部门(SysDept)表控制层
 *
 * @author makejava
 * @since 2020-12-17 09:52:02
 */
@RestController
@RequestMapping("sysDept")
public class SysDeptController {
    /**
     * 服务对象
     */
    @Resource
    private DeptService deptService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysDept selectOne(Long id) {
        return this.deptService.queryById(id);
    }

}