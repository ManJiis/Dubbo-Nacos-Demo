package cn.tlh.ex.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author TANG
 */
@Data
public class BrokerMessageLog {
    private String messageId;

    private String message;

    private Integer tryCount;

    private String status;

    private LocalDateTime nextRetry;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}

