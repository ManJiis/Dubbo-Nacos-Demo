package top.b0x0.admin.consumer.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.b0x0.admin.common.pojo.system.SysUser;
import top.b0x0.admin.dao.SysUserDao;

/**
 * @author TANG
 * @since 2021/04/05
 */
@SpringBootTest
public class DaoTest {

    @Autowired(required = false)
    SysUserDao sysUserDao;

    @Test
    public void test() {
        SysUser admin = sysUserDao.findByUsername("admin");
        System.out.println("admin = " + admin);
    }
}
