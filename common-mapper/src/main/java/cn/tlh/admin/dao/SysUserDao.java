package cn.tlh.admin.dao;

import cn.tlh.admin.common.base.vo.req.UserQueryReqVo;
import cn.tlh.admin.common.pojo.system.SysUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 系统用户(SysUser)表数据库访问层
 *
 * @author TANG
 * @since 2020-12-17 09:51:55
 */
public interface SysUserDao {


    /**
     * 根据用户名查询
     * @param username 用户名
     * @return /
     */
    SysUser findByUsername(String username);

    /**
     * 根据邮箱查询
     * @param email 邮箱
     * @return /
     */
    SysUser findByEmail(String email);

    /**
     * 根据手机号查询
     * @param phone 手机号
     * @return /
     */
    SysUser findByPhone(String phone);

    /**
     * 修改密码
     * @param username 用户名
     * @param pass 密码
     * @param lastPasswordResetTime /
     */
    // update sys_user set password = ?2 , pwd_reset_time = ?3 where username = ?1
    void updatePass(String username, String pass, Date lastPasswordResetTime);

    /**
     * 修改邮箱
     * @param username 用户名
     * @param email 邮箱
     */
   // update sys_user set email = ?2 where username = ?1
    void updateEmail(String username, String email);

    /**
     * 根据角色查询用户
     * @param roleId /
     * @return /
     */
    // SELECT u.* FROM sys_user u, sys_users_roles r WHERE u.user_id = r.user_id AND r.role_id = ?1
    List<SysUser> findByRoleId(Long roleId);

    /**
     * 根据角色中的部门查询
     * @param id /
     * @return /
     */
    // SELECT u.* FROM sys_user u, sys_users_roles r, sys_roles_depts d WHERE u.user_id = r.user_id AND r.role_id = d.role_id AND r.role_id = ?1 group by u.user_id
    List<SysUser> findByDeptRoleId(Long id);

    /**
     * 根据菜单查询
     * @param id 菜单ID
     * @return /
     */
    // SELECT u.* FROM sys_user u, sys_users_roles ur, sys_roles_menus rm WHERE u.user_id = ur.user_id AND ur.role_id = rm.role_id AND rm.menu_id = ?1 group by u.user_id
    List<SysUser> findByMenuId(Long id);

    /**
     * 根据Id删除
     * @param ids /
     */
    void deleteAllByIdIn(Set<Long> ids);

    /**
     * 根据岗位查询
     * @param ids /
     * @return /
     */
    // SELECT count(1) FROM sys_user u, sys_users_jobs j WHERE u.user_id = j.user_id AND j.job_id IN ?1
    int countByJobs(Set<Long> ids);

    /**
     * 根据部门查询
     * @param deptIds /
     * @return /
     */
    // SELECT count(1) FROM sys_user u WHERE u.dept_id IN ?1
    int countByDepts(Set<Long> deptIds);

    /**
     * 根据角色查询
     * @param ids /
     * @return /
     */
    // SELECT count(1) FROM sys_user u, sys_users_roles r WHERE u.user_id = r.user_id AND r.role_id in ?1
    int countByRoles(Set<Long> ids);

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    SysUser queryById(Long userId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param page   分页对象
     * @param userQueryReqVo 实例对象
     * @return 对象列表
     */
    Page<SysUser> selectList(Page<SysUser> page, @Param("req") UserQueryReqVo userQueryReqVo);

    /**
     * 新增数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int insert(SysUser sysUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysUser> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SysUser> entities);

    /**
     * 修改数据
     *
     * @param sysUser 实例对象
     * @return 影响行数
     */
    int update(SysUser sysUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

}