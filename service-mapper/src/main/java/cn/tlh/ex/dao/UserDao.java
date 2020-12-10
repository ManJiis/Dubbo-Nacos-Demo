package cn.tlh.ex.dao;

import cn.tlh.ex.common.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {

    /**
     * 注册，插入数据
     *
     * @param user
     */
    void insertUser(User user);

    /**
     * 根据邮箱查询
     *
     * @param email
     * @return
     */
    User queryByEmail(String email);
}