package top.b0x0.admin.service.system;

import top.b0x0.admin.common.dto.RoleDto;
import top.b0x0.admin.common.vo.req.RoleReqVo;
import top.b0x0.admin.common.dto.RoleSmallDto;
import top.b0x0.admin.common.pojo.system.SysRole;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * @author TANG
 * @date 2020-12-03
 */
public interface RoleService {

    /**
     * 根据ID查询
     *
     * @param id /
     * @return /
     */
    RoleDto findById(long id);

    /**
     * 创建
     *
     * @param resources /
     */
    void create(SysRole resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(SysRole resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据用户ID查询
     *
     * @param id 用户ID
     * @return /
     */
    List<RoleSmallDto> findByUsersId(Long id);

    /**
     * 根据角色查询角色级别
     *
     * @param roles /
     * @return /
     */
    Integer findByRoles(Set<SysRole> roles);

    /**
     * 修改绑定的菜单
     *
     * @param resources /
     * @param roleDTO   /
     */
    void updateMenu(SysRole resources, RoleDto roleDTO);

    /**
     * 解绑菜单
     *
     * @param id /
     */
    void untiedMenu(Long id);


    /**
     * 查询全部
     *
     * @param roleReqVo 条件
     * @return /
     */
    IPage<SysRole> selectList(RoleReqVo roleReqVo);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<RoleDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取用户权限信息
     *
     * @param user 用户信息
     * @return 权限信息
     */
//    List<GrantedAuthority> mapToGrantedAuthorities(UserDto user);

    /**
     * 验证是否被用户关联
     *
     * @param ids /
     */
    void verification(Set<Long> ids);

    /**
     * 根据菜单Id查询
     *
     * @param menuIds /
     * @return /
     */
    List<SysRole> findInMenuId(List<Long> menuIds);
}
