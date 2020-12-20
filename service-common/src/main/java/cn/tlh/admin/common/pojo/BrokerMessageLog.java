package cn.tlh.admin.common.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author TANG
 */
@Data
@TableName("sys_broker_message_log")
public class BrokerMessageLog {
    private String messageId;

    private String message;

    private Integer tryCount;

    private String status;

    private LocalDateTime nextRetry;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}

