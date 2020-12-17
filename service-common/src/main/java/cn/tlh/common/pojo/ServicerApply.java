package cn.tlh.common.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 服务商审核表
 *
 * @author yilin
 * @since 1.0 2019-02-26
 */
@TableName("t_servicer_apply")
public class ServicerApply implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId()
    @ApiModelProperty("")
    private String id;

    @ApiModelProperty("服务商号")
    private String servicerId;

    @ApiModelProperty("审核状态 0:等待总部审核  1:总部通过(等待渠道审核）  2:总部驳回 3:总部拒绝  4: 总部通过")
    private Integer stateAudit;

    @ApiModelProperty("总部审批结果")
    private String hqResult;

    @ApiModelProperty("总部审批时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime hqAcceptTime;

    @ApiModelProperty("送审意见")
    private String auditComment;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


    public String getId() {
        return id;
    }

    public ServicerApply setId(String id) {
        this.id = id;
        return this;
    }

    public String getServicerId() {
        return servicerId;
    }

    public ServicerApply setServicerId(String servicerId) {
        this.servicerId = servicerId;
        return this;
    }

    public Integer getStateAudit() {
        return stateAudit;
    }

    public ServicerApply setStateAudit(Integer stateAudit) {
        this.stateAudit = stateAudit;
        return this;
    }

    public String getHqResult() {
        return hqResult;
    }

    public ServicerApply setHqResult(String hqResult) {
        this.hqResult = hqResult;
        return this;
    }

    public LocalDateTime getHqAcceptTime() {
        return hqAcceptTime;
    }

    public void setHqAcceptTime(LocalDateTime hqAcceptTime) {
        this.hqAcceptTime = hqAcceptTime;
    }

    public String getAuditComment() {
        return auditComment;
    }

    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }


    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public ServicerApply setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

}
