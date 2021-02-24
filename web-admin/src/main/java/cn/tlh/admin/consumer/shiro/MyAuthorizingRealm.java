package cn.tlh.admin.consumer.shiro;

import cn.hutool.core.util.ArrayUtil;
import cn.tlh.admin.common.pojo.system.SysRole;
import cn.tlh.admin.common.pojo.system.SysUser;
import cn.tlh.admin.common.util.constants.RabbitMqConstants;
import cn.tlh.admin.dao.SysMenuDao;
import cn.tlh.admin.dao.SysRoleDao;
import cn.tlh.admin.dao.SysUserDao;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * 自定义Realm
 *
 * @author TANG
 * @date 20120-12-21
 */
@Component
public class MyAuthorizingRealm extends AuthorizingRealm implements Authorizer {

    private final Logger log = LoggerFactory.getLogger(MyAuthorizingRealm.class);

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
        log.info("==============授权---登录用户的信息===============:{}", JSON.toJSONString(user));
        Set<String> permsList = new HashSet<>();
        // 系统管理员，拥有全部权限
        if (ArrayUtil.contains(RabbitMqConstants.ADMINS, userId)) {
            permsList = sysMenuDao.findAllPerm();
            log.info("==============授权---系统管理员，拥有全部权限===============:{}", JSON.toJSONString(permsList));
        } else {
            // 用户对应多个角色
            Set<SysRole> roles = sysRoleDao.findByUserId(userId);
            for (SysRole sysRole : roles) {
                String[] permArray = sysRole.getPerm().split(",");
                // 合并权限
                permsList.addAll(new HashSet<>(Arrays.asList(permArray)));
            }
            log.info("==============授权---拥有什么权限===============:{}", JSON.toJSONString(permsList));
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
        log.info(
                "\n==============认证---token===============:\r\n" +
                        JSON.toJSONString(authToken) + "\n\r" +
                        "=========================================");
        // 查询用户信息
//        SysUser user = sysUserDao.findByPhone((String) authToken.getPrincipal());
        SysUser user = sysUserDao.findByUsername((String) authToken.getPrincipal());
        log.info(
                "\n==============认证---用户信息===============\n\r" +
                        JSON.toJSONString(user) + "\r\n" +
                        "=========================================");
        if (user == null || StringUtils.isBlank(user.getPassword())) {
            throw new UnknownAccountException();
        }
        if (user.getEnabled() == RabbitMqConstants.SYS_USER_STATUS_PROHIBIT) {
            throw new LockedAccountException();
        }
        ByteSource byteSource = new ByteSource(user.getSalt());
//        ByteSource byteSource = ByteSource.Util.bytes(user.getSalt());
        return new SimpleAuthenticationInfo(user, user.getPassword(), byteSource, getName());
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtils.HASH_ALGORITHM_NAME);
        // 散列次数
        shaCredentialsMatcher.setHashIterations(ShiroUtils.HASH_ITERATIONS);
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

    /**
     * 清除当前授权缓存
     *
     * @param principalCollection /
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
        super.clearCachedAuthorizationInfo(principalCollection);
    }

    /**
     * 清除当前用户身份认证缓存
     *
     * @param principalCollection /
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principalCollection) {
        super.clearCachedAuthenticationInfo(principalCollection);
    }

    @Override
    public void clearCache(PrincipalCollection principalCollection) {
        super.clearCache(principalCollection);
    }

    public static void main(String[] args) {
        String s = DigestUtils.sha256Hex("123456" + "RGrgG00X1E0tUxJKhE5y");
        for (int i = 0; i <= 15; i++) {
            s = DigestUtils.sha256Hex(s + "RGrgG00X1E0tUxJKhE5y");
        }
        System.out.println(s);

        SimpleHash simpleHash = new SimpleHash(ShiroUtils.HASH_ALGORITHM_NAME, "123456", "RGrgG00X1E0tUxJKhE5y", 16);
        System.out.println(simpleHash);
    }
}