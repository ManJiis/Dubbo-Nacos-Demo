package top.b0x0.admin.dao;

import top.b0x0.admin.common.pojo.system.EmailConfig;

/**
 * @author TANG
 * @description:
 * @date: 2020-12-19
 */
public interface EmailConfigDao {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    EmailConfig queryById(Long id);

    /**
     * 新增数据
     *
     * @param config 实例对象
     * @return 影响行数
     */
    EmailConfig insert(EmailConfig config);
}
