package cn.tlh.consumer.controller.system;

import cn.tlh.common.pojo.system.SysMenu;
import cn.tlh.service.system.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 系统菜单(SysMenu)表控制层
 *
 * @author makejava
 * @since 2020-12-17 09:52:00
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private MenuService menuService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysMenu selectOne(Long id) {
        return this.menuService.queryById(id);
    }

}