package cn.tlh.admin.dao;

import cn.tlh.admin.common.base.vo.req.RoleVo;
import cn.tlh.admin.common.pojo.system.SysRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 角色表(SysRole)表数据库访问层
 *
 * @author TANG
 * @since 2020-12-17 09:51:57
 */
public interface SysRoleDao {

    /**
     * 根据名称查询
     *
     * @param name /
     * @return /
     */
    SysRole findByName(String name);

    /**
     * 删除多个角色
     *
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);

    /**
     * 根据用户ID查询
     *
     * @param id 用户ID
     * @return /
     */
   // SELECT r.* FROM sys_role r, sys_users_roles u WHERE r.role_id = u.role_id AND u.user_id =
    Set<SysRole> findByUserId(Long id);

    /**
     * 解绑角色菜单
     *
     * @param id 菜单ID
     */
    // delete from sys_roles_menus where menu_id =
    void untiedMenu(Long id);

    /**
     * 根据部门查询
     *
     * @param deptIds /
     * @return /
     */
    // select count(1) from sys_role r, sys_roles_depts d where r.role_id = d.role_id and d.dept_id in
    int countByDepts(Set<Long> deptIds);

    /**
     * 根据菜单Id查询
     *
     * @param menuIds /
     * @return /
     */
    // SELECT r.* FROM sys_role r, sys_roles_menus m WHERE r.role_id = m.role_id AND m.menu_id in
    List<SysRole> findInMenuId(List<Long> menuIds);

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    SysRole queryById(Long roleId);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param page 分页对象
     * @param roleVo 实例对象
     * @return 对象列表
     */
    IPage<SysRole> selectList(Page<SysRole> page, RoleVo roleVo);

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 影响行数
     */
    int insert(SysRole sysRole);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRole> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysRole> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRole> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SysRole> entities);

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 影响行数
     */
    int update(SysRole sysRole);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 影响行数
     */
    int deleteById(Long roleId);

}