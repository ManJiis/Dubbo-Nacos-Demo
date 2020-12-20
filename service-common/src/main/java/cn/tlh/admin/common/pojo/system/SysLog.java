package cn.tlh.admin.common.pojo.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 系统日志(SysLog)实体类
 *
 * @author TANG
 * @since 2020-12-17
 */
@Getter
@Setter
@Table(name = "sys_log")
@NoArgsConstructor
public class SysLog implements Serializable {
    private static final long serialVersionUID = -72316435568397636L;
    /**
     * ID
     */
    // @Id
    // @Column(name = "log_id")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 描述
     */
    private String description;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数
     */
    private String params;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 地址
     */
    private String address;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 异常详细
     */
    private byte[] exceptionDetail;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public SysLog(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}