package top.b0x0.admin.common.pojo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * rabbitmq 死信消息载体
 *
 * @author TANG
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DlxMessage implements Serializable {
    private static final long serialVersionUID = 9956432152000L;

    /** 消息唯一Id  IdUtils.snowflakeId().toString() */
    private String messageId;
    private String exchange;
    private String queue;
    private String routingKey;
    /** 消息 */
    private String message;
    /** 过期时间，单位毫秒 */
    private Long expiration;

}
