
package top.b0x0.admin.service.module.system;

//import cn.tlh.common.utils.FileProperties;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import top.b0x0.admin.common.dto.DataScope;
import top.b0x0.admin.common.dto.RoleSmallDto;
import top.b0x0.admin.common.dto.UserDto;
import top.b0x0.admin.common.exception.EntityExistException;
import top.b0x0.admin.common.exception.EntityNotFoundException;
import top.b0x0.admin.common.pojo.system.SysUser;
import top.b0x0.admin.common.util.StringUtils;
import top.b0x0.admin.common.util.constants.CommonConstants;
import top.b0x0.admin.common.util.constants.RedisCacheKey;
import top.b0x0.admin.common.vo.req.UserQueryReq;
import top.b0x0.admin.dao.SysUserDao;
import top.b0x0.admin.service.util.RedisUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TANG
 * @date 2020-11-23
 */
@Service(version = "${service.version}")
@Component
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    //    @Autowired
//    FileProperties properties;
    @Autowired(required = false)
    RedisUtils redisUtils;

//    private final UserCacheClean userCacheClean;
//    private final OnlineUserService onlineUserService;

    @Autowired(required = false)
    SysUserDao sysUserDao;

    /**
     * 根据手机号查询
     *
     * @param phone phone
     * @return /
     */
    @Override
    public SysUser selectByPhone(String phone) {
        return sysUserDao.findByPhone(phone);
    }

    @Override
    public PageInfo<SysUser> selectList(UserQueryReq userQueryReq) {
        PageHelper.startPage(userQueryReq.getPageNum(), userQueryReq.getPageSize());
        List<SysUser> userList = sysUserDao.listUser(new DataScope());
        return new PageInfo<>(userList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser findById(long id) {
        return sysUserDao.queryById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysUser resources, SysUser currentUser) {
        if (sysUserDao.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(SysUser.class, "username", resources.getUsername());
        }
        if (sysUserDao.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(SysUser.class, "email", resources.getEmail());
        }
        if (sysUserDao.findByPhone(resources.getPhone()) != null) {
            throw new EntityExistException(SysUser.class, "phone", resources.getPhone());
        }
        resources.setCreateTime(LocalDateTime.now());
        resources.setUpdateTime(LocalDateTime.now());
        resources.setCreateBy(currentUser.getUserId().toString());
        resources.setUpdateBy(currentUser.getUserId().toString());
        sysUserDao.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser resources) {
        SysUser sysUser = sysUserDao.queryById(resources.getUserId());
        SysUser user1 = sysUserDao.findByUsername(resources.getUsername());
        SysUser user2 = sysUserDao.findByEmail(resources.getEmail());
        SysUser user3 = sysUserDao.findByPhone(resources.getPhone());
        if (user1 != null && !sysUser.getUserId().equals(user1.getUserId())) {
            throw new EntityExistException(SysUser.class, "username", resources.getUsername());
        }
        if (user2 != null && !sysUser.getUserId().equals(user2.getUserId())) {
            throw new EntityExistException(SysUser.class, "email", resources.getEmail());
        }
        if (user3 != null && !sysUser.getUserId().equals(user3.getUserId())) {
            throw new EntityExistException(SysUser.class, "phone", resources.getPhone());
        }
        // 如果用户的角色改变
        if (!resources.getRoles().equals(sysUser.getRoles())) {
            redisUtils.del(RedisCacheKey.DATE_USER + resources.getUserId());
            redisUtils.del(RedisCacheKey.MENU_USER + resources.getUserId());
            redisUtils.del(RedisCacheKey.ROLE_AUTH + resources.getUserId());
        }
        // 如果用户名称修改
        if (!resources.getUsername().equals(sysUser.getUsername())) {
            redisUtils.del("sysUser::username:" + sysUser.getUsername());
        }
        // 如果用户被禁用，则清除用户登录信息
        if (resources.getEnabled() == CommonConstants.SYS_USER_STATUS_PROHIBIT) {
//            onlineUserService.kickOutForUsername(resources.getUsername());
        }
        sysUser.setUsername(resources.getUsername());
        sysUser.setEmail(resources.getEmail());
        sysUser.setEnabled(resources.getEnabled());
        sysUser.setRoles(resources.getRoles());
        sysUser.setPhone(resources.getPhone());
        sysUser.setNickName(resources.getNickName());
        sysUser.setGender(resources.getGender());
        sysUserDao.insert(sysUser);
        // 清除缓存
        delCaches(sysUser.getUserId(), sysUser.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(SysUser resources) {
        SysUser SysUser = sysUserDao.queryById(resources.getUserId());
        SysUser user1 = sysUserDao.findByPhone(resources.getPhone());
        if (user1 != null && !SysUser.getUserId().equals(user1.getUserId())) {
            throw new EntityExistException(SysUser.class, "phone", resources.getPhone());
        }
        SysUser.setNickName(resources.getNickName());
        SysUser.setPhone(resources.getPhone());
        SysUser.setGender(resources.getGender());
        sysUserDao.insert(SysUser);
        // 清理缓存
        delCaches(SysUser.getUserId(), SysUser.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            // 清理缓存
            SysUser SysUser = findById(id);
            delCaches(SysUser.getUserId(), SysUser.getUsername());
        }
        sysUserDao.deleteAllByIdIn(ids);
    }

    @Override
    public SysUser findByName(String userName) {
        SysUser SysUser = sysUserDao.findByUsername(userName);
        if (SysUser == null) {
            throw new EntityNotFoundException(SysUser.class, "name", userName);
        } else {
            return SysUser;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String pass) {
        sysUserDao.updatePass(username, pass, new Date());
//        redisUtils.del("SysUser::username:" + username);
        flushCache(username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> updateAvatar(MultipartFile multipartFile) {
//        String currentUsername = SecurityUtils.getCurrentUsername();
        SysUser sysUser = sysUserDao.findByUsername("currentUsername");
        String oldPath = sysUser.getAvatarPath();
//        File file = FileUtil.upload(multipartFile, properties.getPath().getAvatar());
        File file = new File(oldPath);
        sysUser.setAvatarPath(Objects.requireNonNull(file).getPath());
        sysUser.setAvatarName(file.getName());
        sysUserDao.insert(sysUser);
        if (StringUtils.isNotBlank(oldPath)) {
//            FileUtil.del(oldPath);
        }
//        @NotBlank String username = sysUser.getUsername();
        String username = sysUser.getUsername();
//        redisUtils.del(CacheKey.USER_NAME + username);
        flushCache(username);
        return new HashMap<String, String>(1) {{
            put("avatar", file.getName());
        }};
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        sysUserDao.updateEmail(username, email);
//        redisUtils.del(CacheKey.USER_NAME + username);
        flushCache(username);
    }

    @Override
    public void download(List<UserDto> queryAll, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserDto userDTO : queryAll) {
            List<String> roles = userDTO.getRoles().stream().map(RoleSmallDto::getName).collect(Collectors.toList());
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", userDTO.getUsername());
            map.put("角色", roles);
            map.put("邮箱", userDTO.getEmail());
            map.put("状态", userDTO.getEnabled() == 1 ? "启用" : "禁用");
            map.put("手机号码", userDTO.getPhone());
            map.put("修改密码的时间", userDTO.getPwdResetTime());
            map.put("创建日期", userDTO.getCreateTime());
            list.add(map);
        }
//        FileUtil.downloadExcel(list, response);
    }

    /**
     * 清理缓存
     *
     * @param id /
     */
    public void delCaches(Long id, String username) {
        redisUtils.del(RedisCacheKey.USER_ID + id);
        redisUtils.del(RedisCacheKey.USER_NAME + username);
        flushCache(username);
    }

    /**
     * 清理 登陆时 用户缓存信息
     *
     * @param username /
     */
    private void flushCache(String username) {
//        userCacheClean.cleanUserCache(username);
    }
}
