package top.b0x0.admin.consumer.controller.system;

import cn.hutool.core.lang.Dict;
import top.b0x0.admin.common.vo.BusinessResponse;
import top.b0x0.admin.common.dto.RoleDto;
import top.b0x0.admin.common.mapstruct.RoleMapper;
import top.b0x0.admin.common.vo.req.RoleReqVo;
import top.b0x0.admin.common.exception.BusinessErrorException;
import top.b0x0.admin.common.pojo.system.SysRole;
import top.b0x0.admin.consumer.annotaion.Log;
import top.b0x0.admin.service.system.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author TANG
 * @date 2020-12-03
 */
@RestController
@Api(tags = "系统：角色管理")
@RequestMapping("system/role")
public class RoleController {

    @Reference(version = "${service.version}", check = false)
    RoleService roleService;

    @Autowired(required = false)
    RoleMapper roleMapper;

    private static final String ENTITY_NAME = "SysRole";

    @ApiOperation("获取单个role")
    @GetMapping(value = "/{id}")
    // @RequiresPermissions("@el.check('roles:list')")
    public BusinessResponse query(@PathVariable Long id) {
        return BusinessResponse.ok(roleService.findById(id));
    }

    @ApiOperation("导出角色数据")
    @GetMapping(value = "/download")
    // @RequiresPermissions("@el.check('SysRole:list')")
    public void download(HttpServletResponse response, RoleReqVo roleReqVo) throws IOException {
        List<SysRole> sysRoles = roleService.selectList(roleReqVo).getRecords();
        List<RoleDto> roleDtoList = roleMapper.toDto(sysRoles);
        roleService.download(roleDtoList, response);
    }

    @ApiOperation("查询角色")
    @GetMapping
    // @RequiresPermissions("@el.check('roles:list')")
    public BusinessResponse query(RoleReqVo roleReqVo) {
        return BusinessResponse.ok(roleService.selectList(roleReqVo));
    }

    @ApiOperation("获取用户级别")
    @GetMapping(value = "/level")
    public BusinessResponse getLevel() {
        return BusinessResponse.ok(Dict.create().set("level", getLevels(null)));
    }

    @Log(description = "新增角色")
    @ApiOperation("新增角色")
    @PostMapping
    // @RequiresPermissions("@el.check('roles:add')")
    public BusinessResponse create(@Validated @RequestBody SysRole resources) {
        if (resources.getRoleId() != null) {
            throw new BusinessErrorException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        getLevels(resources.getLevel());
        roleService.create(resources);
        return BusinessResponse.ok();
    }

    @Log(description = "修改角色")
    @ApiOperation("修改角色")
    @PutMapping
    // @RequiresPermissions("@el.check('roles:edit')")
    public BusinessResponse update(@Validated(SysRole.class) @RequestBody SysRole resources) {
        getLevels(resources.getLevel());
        roleService.update(resources);
        return BusinessResponse.ok();
    }

    @Log(description = "修改角色菜单")
    @ApiOperation("修改角色菜单")
    @PutMapping(value = "/menu")
    // @RequiresPermissions("@el.check('roles:edit')")
    public BusinessResponse updateMenu(@RequestBody SysRole resources) {
        RoleDto SysRole = roleService.findById(resources.getRoleId());
        getLevels(SysRole.getLevel());
        roleService.updateMenu(resources, SysRole);
        return BusinessResponse.ok();
    }

    @Log(description = "删除角色")
    @ApiOperation("删除角色")
    @DeleteMapping
    // @RequiresPermissions("@el.check('roles:del')")
    public BusinessResponse delete(@RequestBody Set<Long> ids) {
        for (Long id : ids) {
            RoleDto SysRole = roleService.findById(id);
            getLevels(SysRole.getLevel());
        }
        // 验证是否被用户关联
        roleService.verification(ids);
        roleService.delete(ids);
        return BusinessResponse.ok();
    }

    /**
     * 获取用户的角色级别
     *
     * @return
     */
    private int getLevels(Integer level) {
//        List<Integer> levels = roleService.findByUsersId(SecurityUtils.getCurrentUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList());
        List<Integer> levels = new ArrayList<>();
        int min = Collections.min(levels);
        if (level != null) {
            if (level < min) {
                throw new BusinessErrorException("权限不足，你的角色级别：" + min + "，低于操作的角色级别：" + level);
            }
        }
        return min;
    }
}
