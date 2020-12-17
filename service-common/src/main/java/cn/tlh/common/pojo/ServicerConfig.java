package cn.tlh.common.pojo;

import cn.tlh.common.vo.req.ServicerUser;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 服务商配置表(生成登陆账号)
 *
 * @author 蛮吉
 */
@TableName("t_servicer_config")
public class ServicerConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.UUID)
    @ApiModelProperty("")
    private String id;

    @ApiModelProperty("服务商号")
    private String servicerId;

    @ApiModelProperty("供应商名称")
    private String supName;

    @ApiModelProperty("接入地址")
    private String systemAccessAddress;

    @ApiModelProperty("自助查询平台")
    private String queryPlatform;

    @ApiModelProperty("登录账号")
    private String phone;

    @ApiModelProperty("接入密码")
    private String password;

    @ApiModelProperty("api对接密钥")
    private String apiDockingKey;

    @ApiModelProperty("指向邮箱")
    private String emali;

    @ApiModelProperty("推广链接")
    private String promotionLink;

    @ApiModelProperty("操作人id")
    private String operator;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    @TableField(exist = false)
    private List<ServicerUser> servicerUserList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServicerId() {
        return servicerId;
    }

    public void setServicerId(String servicerId) {
        this.servicerId = servicerId;
    }

    public String getSupName() {
        return supName;
    }

    public void setSupName(String supName) {
        this.supName = supName;
    }

    public String getSystemAccessAddress() {
        return systemAccessAddress;
    }

    public void setSystemAccessAddress(String systemAccessAddress) {
        this.systemAccessAddress = systemAccessAddress;
    }

    public String getQueryPlatform() {
        return queryPlatform;
    }

    public void setQueryPlatform(String queryPlatform) {
        this.queryPlatform = queryPlatform;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiDockingKey() {
        return apiDockingKey;
    }

    public void setApiDockingKey(String apiDockingKey) {
        this.apiDockingKey = apiDockingKey;
    }

    public String getEmali() {
        return emali;
    }

    public void setEmali(String emali) {
        this.emali = emali;
    }

    public String getPromotionLink() {
        return promotionLink;
    }

    public void setPromotionLink(String promotionLink) {
        this.promotionLink = promotionLink;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public List<ServicerUser> getServicerUserList() {
        return servicerUserList;
    }

    public void setServicerUserList(List<ServicerUser> servicerUserList) {
        this.servicerUserList = servicerUserList;
    }
}
