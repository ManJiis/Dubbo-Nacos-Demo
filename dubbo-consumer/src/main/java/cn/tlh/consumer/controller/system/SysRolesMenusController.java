package cn.tlh.consumer.controller.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 角色菜单关联(SysRolesMenus)表控制层
 *
 * @author makejava
 * @since 2020-12-17 09:51:57
 */
@RestController
@RequestMapping("sysRolesMenus")
public class SysRolesMenusController {
    /**
     * 服务对象
     */
    @Resource
    private SysRolesMenusService sysRolesMenusService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysRolesMenus selectOne(Long id) {
        return this.sysRolesMenusService.queryById(id);
    }

}