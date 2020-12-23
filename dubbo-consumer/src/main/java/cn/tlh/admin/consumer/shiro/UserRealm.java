package cn.tlh.admin.consumer.shiro;

import cn.hutool.core.util.ArrayUtil;
import cn.tlh.admin.common.base.dto.RoleDto;
import cn.tlh.admin.common.pojo.system.SysRole;
import cn.tlh.admin.common.pojo.system.SysUser;
import cn.tlh.admin.common.util.AdminConstants;
import cn.tlh.admin.dao.SysMenuDao;
import cn.tlh.admin.dao.SysRoleDao;
import cn.tlh.admin.dao.SysUserDao;
import cn.tlh.admin.service.system.RoleService;
import cn.tlh.admin.service.system.UserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @author TANG
 * @description 自定义Realm
 * @date 20120-12-21
 */
@Component
public class UserRealm extends AuthorizingRealm implements Authorizer {

    private final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired(required = false)
    SysMenuDao sysMenuDao;
    @Autowired(required = false)
    SysUserDao sysUserDao;
    @Autowired(required = false)
    SysRoleDao sysRoleDao;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SysUser user = (SysUser) principal.getPrimaryPrincipal();
        Long userId = user.getUserId();
        logger.info("==============授权---登录用户的信息===============:{}", JSON.toJSONString(user));
        Set<String> permsList = new HashSet<>();
        // 系统管理员，拥有全部权限
        if (ArrayUtil.contains(AdminConstants.ADMINS, userId)) {
            permsList = sysMenuDao.findAllPerm();
            logger.info("==============授权---系统管理员，拥有全部权限===============:{}", JSON.toJSONString(permsList));
        } else {
            // 用户对应多个角色
            Set<SysRole> roles = sysRoleDao.findByUserId(userId);
            for (SysRole sysRole : roles) {
                String[] permArray = sysRole.getPerm().split(",");
                // 合并权限
                permsList.addAll(new HashSet<>(Arrays.asList(permArray)));
            }
            logger.info("==============授权---拥有什么权限===============:{}", JSON.toJSONString(permsList));
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsList);
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        logger.info("==============认证---token===============:{}", JSON.toJSONString(authToken));
        // 查询用户信息
//        SysUser user = sysUserDao.findByPhone((String) authToken.getPrincipal());
        SysUser user = sysUserDao.findByUsername((String) authToken.getPrincipal());
        logger.info("==============认证---用户信息===============:{}", JSON.toJSONString(user));
        if (user == null || StringUtils.isBlank(user.getPassword())) {
            throw new UnknownAccountException();
        }
        if (user.getEnabled() == AdminConstants.SYS_USER_STATUS_PROHIBIT) {
            throw new LockedAccountException();
        }
//        ByteSource byteSource = ByteSource.Util.bytes(user.getSalt());
        ByteSource byteSource = ByteSource.Util.bytes("RGrgG00X1E0tUxJKhE5y");
        return new SimpleAuthenticationInfo(user, user.getPassword(), byteSource, getName());
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        // 散列次数
        shaCredentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

    public static void main(String[] args) {
        String s = DigestUtils.sha256Hex("123456" + "RGrgG00X1E0tUxJKhE5y");
        for (int i = 0; i <= 15; i++) {
            s = DigestUtils.sha256Hex(s + "RGrgG00X1E0tUxJKhE5y");
        }
        System.out.println(s);

        SimpleHash simpleHash = new SimpleHash(ShiroUtils.hashAlgorithmName, "123456", "RGrgG00X1E0tUxJKhE5y", 16);
        System.out.println(simpleHash);
    }
}