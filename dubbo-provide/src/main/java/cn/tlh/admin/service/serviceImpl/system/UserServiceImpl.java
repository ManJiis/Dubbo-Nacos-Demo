
package cn.tlh.admin.service.serviceImpl.system;

//import cn.tlh.common.utils.FileProperties;

import cn.tlh.admin.common.base.dto.RoleSmallDto;
import cn.tlh.admin.common.base.dto.UserDto;
import cn.tlh.admin.common.base.vo.req.UserVo;
import cn.tlh.admin.common.exception.customexception.EntityExistException;
import cn.tlh.admin.common.exception.customexception.EntityNotFoundException;
import cn.tlh.admin.common.pojo.system.SysUser;
import cn.tlh.admin.common.util.StringUtils;
import cn.tlh.admin.common.util.ValidationUtil;
import cn.tlh.admin.dao.SysUserDao;
import cn.tlh.admin.service.system.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TANG
 * @date 2020-11-23
 */
@Service(version = "${service.version}")
@Component
// @RequiredArgsConstructor
public class UserServiceImpl implements UserService {
//    @Autowired
//    FileProperties properties;
//    @Autowired
//    RedisUtils redisUtils;
//    private final UserCacheClean userCacheClean;
//    private final OnlineUserService onlineUserService;

    @Autowired(required = false)
    SysUserDao sysUserDao;

    /**
     * 根据手机号查询
     *
     * @param phone ID
     * @return /
     */
    @Override
    public SysUser selectByPhone(String phone) {
        return sysUserDao.findByPhone(phone);
    }

    @Override
    public Page<SysUser> selectList(UserVo userVo) {
        Page<SysUser> page = new Page<>(userVo.getCurrentPage(), userVo.getPageSize());
        System.out.println("page.getTotal() = " + page.getTotal());
        System.out.println("page.getPages() = " + page.getPages());
        return sysUserDao.selectList(page, userVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser findById(long id) {
        return sysUserDao.queryById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysUser resources) {
        if (sysUserDao.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(SysUser.class, "username", resources.getUsername());
        }
        if (sysUserDao.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(SysUser.class, "email", resources.getEmail());
        }
        if (sysUserDao.findByPhone(resources.getPhone()) != null) {
            throw new EntityExistException(SysUser.class, "phone", resources.getPhone());
        }
        sysUserDao.insert(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUser resources) {
        SysUser SysUser = sysUserDao.queryById(resources.getUserId());
        ValidationUtil.isNull(SysUser.getUserId(), "SysUser", "id", resources.getUserId());
        SysUser user1 = sysUserDao.findByUsername(resources.getUsername());
        SysUser user2 = sysUserDao.findByEmail(resources.getEmail());
        SysUser user3 = sysUserDao.findByPhone(resources.getPhone());
        if (user1 != null && !SysUser.getUserId().equals(user1.getUserId())) {
            throw new EntityExistException(SysUser.class, "username", resources.getUsername());
        }
        if (user2 != null && !SysUser.getUserId().equals(user2.getUserId())) {
            throw new EntityExistException(SysUser.class, "email", resources.getEmail());
        }
        if (user3 != null && !SysUser.getUserId().equals(user3.getUserId())) {
            throw new EntityExistException(SysUser.class, "phone", resources.getPhone());
        }
        // 如果用户的角色改变
/*        if (!resources.getRoles().equals(SysUser.getRoles())) {
            redisUtils.del(CacheKey.DATE_USER + resources.getUserId());
            redisUtils.del(CacheKey.MENU_USER + resources.getUserId());
            redisUtils.del(CacheKey.ROLE_AUTH + resources.getUserId());
        }*/
        // 如果用户名称修改
        if (!resources.getUsername().equals(SysUser.getUsername())) {
//            redisUtils.del("SysUser::username:" + SysUser.getUsername());
        }
/*        // 如果用户被禁用，则清除用户登录信息
        if(!resources.getEnabled()){
            onlineUserService.kickOutForUsername(resources.getUsername());
        }*/
        SysUser.setUsername(resources.getUsername());
        SysUser.setEmail(resources.getEmail());
        SysUser.setEnabled(resources.getEnabled());
//        SysUser.setRoles(resources.getRoles());
        SysUser.setPhone(resources.getPhone());
        SysUser.setNickName(resources.getNickName());
        SysUser.setGender(resources.getGender());
        sysUserDao.insert(SysUser);
        // 清除缓存
        delCaches(SysUser.getUserId(), SysUser.getUsername());
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
        SysUser SysUser = sysUserDao.findByUsername("currentUsername");
        String oldPath = SysUser.getAvatarPath();
//        File file = FileUtil.upload(multipartFile, properties.getPath().getAvatar());
        File file = new File(oldPath);
        SysUser.setAvatarPath(Objects.requireNonNull(file).getPath());
        SysUser.setAvatarName(file.getName());
        sysUserDao.insert(SysUser);
        if (StringUtils.isNotBlank(oldPath)) {
//            FileUtil.del(oldPath);
        }
        @NotBlank String username = SysUser.getUsername();
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
//        redisUtils.del(CacheKey.USER_ID + id);
//        redisUtils.del(CacheKey.USER_NAME + username);
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
