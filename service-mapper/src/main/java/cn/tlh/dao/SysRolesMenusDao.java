package cn.tlh.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色菜单关联(SysRolesMenus)表数据库访问层
 *
 * @author makejava
 * @since 2020-12-17 09:51:56
 */
public interface SysRolesMenusDao {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    SysRolesMenus queryById(Long menuId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<SysRolesMenus> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysRolesMenus 实例对象
     * @return 对象列表
     */
    List<SysRolesMenus> queryAll(SysRolesMenus sysRolesMenus);

    /**
     * 新增数据
     *
     * @param sysRolesMenus 实例对象
     * @return 影响行数
     */
    int insert(SysRolesMenus sysRolesMenus);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRolesMenus> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<SysRolesMenus> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<SysRolesMenus> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<SysRolesMenus> entities);

    /**
     * 修改数据
     *
     * @param sysRolesMenus 实例对象
     * @return 影响行数
     */
    int update(SysRolesMenus sysRolesMenus);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteById(Long menuId);

}