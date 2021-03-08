package cn.tlh.admin.common.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author TANG
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_broker_message_log")
public class BrokerMessageLog implements Serializable {
    private static final long serialVersionUID = 6315066756360463428L;

    private String messageId;
    private String message;
    private Integer tryCount = 0;
    private String status;
    private LocalDateTime nextRetry;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}

