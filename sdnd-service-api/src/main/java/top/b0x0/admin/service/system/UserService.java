
package top.b0x0.admin.service.system;

import com.github.pagehelper.PageInfo;
import top.b0x0.admin.common.dto.UserDto;
import top.b0x0.admin.common.vo.req.UserQueryReqVo;
import top.b0x0.admin.common.pojo.system.SysUser;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author TANG
 * @date 2020-11-23
 */
public interface UserService {

    /**
     * 根据手机号查询
     *
     * @param phone ID
     * @return /
     */
    SysUser selectByPhone(String phone);

    PageInfo<SysUser> selectList2(UserQueryReqVo userQueryReqVo);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return /
     */
    SysUser findById(long id);

    /**
     * 新增用户
     *
     * @param resources   /
     * @param currentUser 当前登录用户
     */
    void create(SysUser resources, SysUser currentUser);

    /**
     * 编辑用户
     *
     * @param resources /
     */
    void update(SysUser resources);

    /**
     * 删除用户
     *
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据用户名查询
     *
     * @param userName /
     * @return /
     */
    SysUser findByName(String userName);

    /**
     * 修改密码
     *
     * @param username        用户名
     * @param encryptPassword 密码
     */
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     *
     * @param file 文件
     * @return /
     */
    Map<String, String> updateAvatar(MultipartFile file);

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email    邮箱
     */
    void updateEmail(String username, String email);


    /**
     * 分页查询
     *
     * @param userQueryReqVo 条件
     * @return /
     */
    Page<SysUser> selectList(UserQueryReqVo userQueryReqVo);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<UserDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 用户自助修改资料
     *
     * @param resources /
     */
    void updateCenter(SysUser resources);
}
