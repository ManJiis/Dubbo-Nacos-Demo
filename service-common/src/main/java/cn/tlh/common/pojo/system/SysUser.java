package cn.tlh.common.pojo.system;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户(SysUser)实体类
 *
 * @author makejava
 * @since 2020-12-17 09:51:55
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = 649715419116774602L;
    /**
     * ID
     */
    private Long userId;
    /**
     * 部门名称
     */
    private Long deptId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像地址
     */
    private String avatarName;
    /**
     * 头像真实路径
     */
    private String avatarPath;
    /**
     * 密码
     */
    private String password;
    /**
     * 是否为admin账号
     */
    private Object isAdmin;
    /**
     * 状态：1启用、0禁用
     */
    private Long enabled;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新着
     */
    private String updateBy;
    /**
     * 修改密码的时间
     */
    private Date pwdResetTime;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Object isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Long getEnabled() {
        return enabled;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getPwdResetTime() {
        return pwdResetTime;
    }

    public void setPwdResetTime(Date pwdResetTime) {
        this.pwdResetTime = pwdResetTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}