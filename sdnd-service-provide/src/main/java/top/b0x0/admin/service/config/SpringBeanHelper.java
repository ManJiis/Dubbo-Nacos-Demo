//package top.b0x0.admin.service.config;
//
//import top.b0x0.admin.dao.SysRoleDao;
//import top.b0x0.admin.dao.SysUserDao;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
///**
// * @author TANG
// * @since 2021-04-02
// */
//@Component
//public class SpringBeanHelper implements InitializingBean {
//    private static SpringBeanHelper instance;
//
//    @Autowired(required = false)
//    SysUserDao sysUserDao;
//    @Autowired(required = false)
//    SysRoleDao sysRoleDao;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        SpringBeanHelper.instance = this;
//    }
//
//    @PostConstruct
//    public void initialize() {
//        instance = this;
//        instance.sysUserDao = this.sysUserDao;
//        instance.sysRoleDao = this.sysRoleDao;
//    }
//}
