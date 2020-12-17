package cn.tlh.consumer.controller.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色部门关联(SysRolesDepts)表控制层
 *
 * @author makejava
 * @since 2020-12-17 09:51:58
 */
@RestController
@RequestMapping("sysRolesDepts")
public class SysRolesDeptsController {
    /**
     * 服务对象
     */
    @Resource
    private SysRolesDeptsService sysRolesDeptsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysRolesDepts selectOne(Long id) {
        return this.sysRolesDeptsService.queryById(id);
    }

}