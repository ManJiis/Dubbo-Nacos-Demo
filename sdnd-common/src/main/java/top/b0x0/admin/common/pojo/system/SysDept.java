package top.b0x0.admin.common.pojo.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 部门(SysDept)实体类
 *
 * @author TANG
 * @since 2020-12-17 09:52:01
 */
@Getter
@Setter
public class SysDept implements Serializable {
    private static final long serialVersionUID = 769821127108646999L;
    /**
     * ID
     */
    private Long deptId;
    /**
     * 上级部门
     */
    private Long pid;
    /**
     * 子部门数目
     */
    private Integer subCount;
    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer deptSort;
    /**
     * 状态
     */
    private Object enabled;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 创建日期
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;



}