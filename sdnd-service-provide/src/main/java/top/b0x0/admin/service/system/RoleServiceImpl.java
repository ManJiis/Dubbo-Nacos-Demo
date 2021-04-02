
package top.b0x0.admin.service.system;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.b0x0.admin.common.dto.RoleDto;
import top.b0x0.admin.common.dto.RoleSmallDto;
import top.b0x0.admin.common.exception.myexception.BusinessErrorException;
import top.b0x0.admin.common.exception.myexception.EntityExistException;
import top.b0x0.admin.common.mapstruct.RoleMapper;
import top.b0x0.admin.common.mapstruct.RoleSmallMapper;
import top.b0x0.admin.common.pojo.system.SysRole;
import top.b0x0.admin.common.pojo.system.SysUser;
import top.b0x0.admin.common.vo.req.RoleReqVo;
import top.b0x0.admin.dao.SysRoleDao;
import top.b0x0.admin.dao.SysUserDao;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TANG
 * @date 2020-12-03
 */
@Service(version = "${service.version}")
@Component
// @RequiredArgsConstructor
//@CacheConfig(cacheNames = "SysRole")
public class RoleServiceImpl implements RoleService {

//    @Autowired
//    RedisUtils redisUtils;
//    private final UserCacheClean userCacheClean;

    @Autowired(required = false)
    SysRoleDao sysRoleDao;
    @Autowired(required = false)
    SysUserDao sysUserDao;

    @Override
    public IPage<SysRole> selectList(RoleReqVo roleReqVo) {
        Page<SysRole> page = new Page<>(roleReqVo.getPageNum(), roleReqVo.getPageSize());
        return sysRoleDao.selectList(page, roleReqVo);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    @Transactional(rollbackFor = Exception.class)
    public RoleDto findById(long id) {
        SysRole sysRole = sysRoleDao.queryById(id);
        return RoleMapper.MAPPER.toDto(sysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysRole resources) {
        if (sysRoleDao.findByName(resources.getName()) != null) {
            throw new EntityExistException(SysRole.class, "username", resources.getName());
        }
        sysRoleDao.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRole resources) {
        SysRole SysRole = sysRoleDao.queryById(resources.getRoleId());

        SysRole role1 = sysRoleDao.findByName(resources.getName());

        if (role1 != null && !role1.getRoleId().equals(SysRole.getRoleId())) {
            throw new EntityExistException(SysRole.class, "username", resources.getName());
        }
        SysRole.setName(resources.getName());
        SysRole.setDescription(resources.getDescription());
        SysRole.setDataScope(resources.getDataScope());
        SysRole.setLevel(resources.getLevel());
        sysRoleDao.insert(SysRole);
        // 更新相关缓存
        delCaches(SysRole.getRoleId(), null);
    }

    @Override
    public void updateMenu(SysRole resources, RoleDto roleDTO) {
//        SysRole SysRole = roleMapper.toEntity(roleDTO);
        SysRole SysRole = new SysRole();
        List<SysUser> users = sysUserDao.findByRoleId(SysRole.getRoleId());
        // 更新菜单
        delCaches(resources.getRoleId(), users);
        sysRoleDao.insert(SysRole);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void untiedMenu(Long menuId) {
        // 更新菜单
        sysRoleDao.untiedMenu(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            // 更新相关缓存
            delCaches(id, null);
        }
        sysRoleDao.deleteAllByIdIn(ids);
    }

    @Override
    public List<RoleSmallDto> findByUsersId(Long id) {
        return RoleSmallMapper.MAPPER.toDto(new ArrayList<>(sysRoleDao.findByUserId(id)));
    }

    @Override
    public Integer findByRoles(Set<SysRole> roles) {
        if (roles.size() == 0) {
            return Integer.MAX_VALUE;
        }
        Set<RoleDto> roleDtos = new HashSet<>();
        for (SysRole SysRole : roles) {
            roleDtos.add(findById(SysRole.getRoleId()));
        }
        return Collections.min(roleDtos.stream().map(RoleDto::getLevel).collect(Collectors.toList()));
    }

/*    @Override
    // @Cacheable(key = "'auth:' + #p0.id")
    public List<GrantedAuthority> mapToGrantedAuthorities(UserDto SysUser) {
        return new ArrayList<>();
    }*/

    @Override
    public void download(List<RoleDto> roles, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RoleDto SysRole : roles) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("角色名称", SysRole.getName());
            map.put("角色级别", SysRole.getLevel());
            map.put("描述", SysRole.getDescription());
            map.put("创建日期", SysRole.getCreateTime());
            list.add(map);
        }
//        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void verification(Set<Long> ids) {
        if (sysUserDao.countByRoles(ids) > 0) {
            throw new BusinessErrorException("所选角色存在用户关联，请解除关联再试！");
        }
    }

    @Override
    public List<SysRole> findInMenuId(List<Long> menuIds) {
        return sysRoleDao.findInMenuId(menuIds);
    }

    /**
     * 清理缓存
     *
     * @param id /
     */
    public void delCaches(Long id, List<SysUser> users) {
        users = CollectionUtil.isEmpty(users) ? sysUserDao.findByRoleId(id) : users;
        if (CollectionUtil.isNotEmpty(users)) {
//            users.forEach(item -> userCacheClean.cleanUserCache(item.getUsername()));
            Set<Long> userIds = users.stream().map(SysUser::getUserId).collect(Collectors.toSet());
//            redisUtils.delByKeys(CacheKey.DATE_USER, userIds);
//            redisUtils.delByKeys(CacheKey.MENU_USER, userIds);
//            redisUtils.delByKeys(CacheKey.ROLE_AUTH, userIds);
        }
//        redisUtils.del(CacheKey.ROLE_ID + id);
    }
}
