package cn.tlh.common.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 服务商
 *
 * @author yilin
 * @since 1.0 2019-02-26
 */
@TableName("t_servicer")
public class Servicer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId()
    @ApiModelProperty("服务商号")
    private String id;

    @ApiModelProperty("对应sys_company表中的服务商ID")
    private String servicerId;

    @ApiModelProperty("当前工单id，每修改一次都重新生成工单记录，保存工单信息")
    private String applyId;

    @ApiModelProperty("审核状态 0:等待总部审核  1:总部通过(等待渠道审核）  2:总部驳回 3:总部拒绝 4:配置通过 5:正常 6:驳回 7:冻结")
    private Integer stateAudit;

    @ApiModelProperty("服务商名称")
    private String name;

    @ApiModelProperty("服务商简称")
    private String avtName;

    @ApiModelProperty("法人名称")
    private String corpName;

    @ApiModelProperty("法人身份证号")
    private String corpId;

    @ApiModelProperty("法人联系电话")
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$", message = "法人联系电话号不合法")
    private String corpPhone;

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("微信号")
    private String wechatNo;

    @ApiModelProperty("服务城市")
    private String serCity;

    @ApiModelProperty("服务城市地区码")
    private String serAreaCode;

    @ApiModelProperty("结算方式：(0:日结  1:周结   2：月结)  默认：月结")
    private Integer settleMethod;


    @ApiModelProperty("合同到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate contractDueTime;

    @ApiModelProperty("商户基准（单位：万分比） 默认18")
    private Integer merchantCommission;

    @ApiModelProperty("供应商基准（单位：万分比）默认6")
    private Integer supplierCommission;

    @ApiModelProperty("营业执照号")
    private String permitNo;

    @ApiModelProperty("注册地址")
    private String permitAddr;

    @ApiModelProperty("支付回佣   默认是0")
    private BigDecimal paymentCommission;

    @ApiModelProperty("保理协议编号")
    private String protocolNo;

    @ApiModelProperty("支付协议编号")
    private String paymentNo;

    @ApiModelProperty("结算机构  1:总公司   2:分公司" )
    private Integer settleCompany;

    @ApiModelProperty("结算账户名")
    private String settleAccount;

    @ApiModelProperty("账号")
    private String accountNo;

    @ApiModelProperty("开户行")
    private String openBank;

    @ApiModelProperty("业务对接人")
    private String businessDocker;

    @ApiModelProperty("业务对接人电话")
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$", message = "业务对接人电话号不合法")
    private String businessPhone;

    @ApiModelProperty("财务对接人")
    private String technologyDock;

    @ApiModelProperty("财务对接人电话")
    @Pattern(regexp = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$", message = "财务对接人电话号不合法")
    private String technologyPhone;

    @ApiModelProperty("营业执照")
    private String urlBusine;

    @ApiModelProperty("法人身份证正面")
    private String urlCardJust;

    @ApiModelProperty("法人身份证反面")
    private String urlCardBack;

    @ApiModelProperty("推荐函")
    private String urlRecLetter;

    @ApiModelProperty("合同最后一页")
    private String urlContract;

    @ApiModelProperty("推荐理由")
    private String auditComment;

    @ApiModelProperty("进件人员ID")
    private String entryUserId;

    @ApiModelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("管辖机构名称")
    @TableField(exist = false)
    private String companyName;

    @ApiModelProperty("管辖机构id")
    private String companyId;

    @ApiModelProperty("评级   1:低    2:中     3:高     现在默认为空")
    private Integer servicerGrade;

    @ApiModelProperty("状态  1:正常 2:驳回 3:冻结 4:等待审核")
    private Integer state;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getStateAudit() {
        return stateAudit;
    }

    public void setStateAudit(Integer stateAudit) {
        this.stateAudit = stateAudit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvtName() {
        return avtName;
    }

    public void setAvtName(String avtName) {
        this.avtName = avtName;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpPhone() {
        return corpPhone;
    }

    public void setCorpPhone(String corpPhone) {
        this.corpPhone = corpPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo;
    }

    public String getSerCity() {
        return serCity;
    }

    public void setSerCity(String serCity) {
        this.serCity = serCity;
    }

    public String getSerAreaCode() {
        return serAreaCode;
    }

    public void setSerAreaCode(String serAreaCode) {
        this.serAreaCode = serAreaCode;
    }

    public Integer getSettleMethod() {
        return settleMethod;
    }

    public void setSettleMethod(Integer settleMethod) {
        this.settleMethod = settleMethod;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public LocalDate getContractDueTime() {
        return contractDueTime;
    }

    public void setContractDueTime(LocalDate contractDueTime) {
        this.contractDueTime = contractDueTime;
    }


    public Integer getMerchantCommission() {
        return merchantCommission;
    }

    public void setMerchantCommission(Integer merchantCommission) {
        this.merchantCommission = merchantCommission;
    }

    public Integer getSupplierCommission() {
        return supplierCommission;
    }

    public void setSupplierCommission(Integer supplierCommission) {
        this.supplierCommission = supplierCommission;
    }

    public String getPermitNo() {
        return permitNo;
    }

    public void setPermitNo(String permitNo) {
        this.permitNo = permitNo;
    }

    public String getPermitAddr() {
        return permitAddr;
    }

    public void setPermitAddr(String permitAddr) {
        this.permitAddr = permitAddr;
    }

    public BigDecimal getPaymentCommission() {
        return paymentCommission;
    }

    public void setPaymentCommission(BigDecimal paymentCommission) {
        this.paymentCommission = paymentCommission;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Integer getSettleCompany() {
        return settleCompany;
    }

    public void setSettleCompany(Integer settleCompany) {
        this.settleCompany = settleCompany;
    }

    public String getSettleAccount() {
        return settleAccount;
    }

    public void setSettleAccount(String settleAccount) {
        this.settleAccount = settleAccount;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getOpenBank() {
        return openBank;
    }

    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    public String getBusinessDocker() {
        return businessDocker;
    }

    public void setBusinessDocker(String businessDocker) {
        this.businessDocker = businessDocker;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getTechnologyDock() {
        return technologyDock;
    }

    public void setTechnologyDock(String technologyDock) {
        this.technologyDock = technologyDock;
    }

    public String getTechnologyPhone() {
        return technologyPhone;
    }

    public void setTechnologyPhone(String technologyPhone) {
        this.technologyPhone = technologyPhone;
    }

    public String getUrlBusine() {
        return urlBusine;
    }

    public void setUrlBusine(String urlBusine) {
        this.urlBusine = urlBusine;
    }

    public String getUrlCardJust() {
        return urlCardJust;
    }

    public void setUrlCardJust(String urlCardJust) {
        this.urlCardJust = urlCardJust;
    }

    public String getUrlCardBack() {
        return urlCardBack;
    }

    public void setUrlCardBack(String urlCardBack) {
        this.urlCardBack = urlCardBack;
    }

    public String getUrlRecLetter() {
        return urlRecLetter;
    }

    public void setUrlRecLetter(String urlRecLetter) {
        this.urlRecLetter = urlRecLetter;
    }

    public String getUrlContract() {
        return urlContract;
    }

    public void setUrlContract(String urlContract) {
        this.urlContract = urlContract;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }

    public String getEntryUserId() {
        return entryUserId;
    }

    public void setEntryUserId(String entryUserId) {
        this.entryUserId = entryUserId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getServicerGrade() {
        return servicerGrade;
    }

    public void setServicerGrade(Integer servicerGrade) {
        this.servicerGrade = servicerGrade;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
