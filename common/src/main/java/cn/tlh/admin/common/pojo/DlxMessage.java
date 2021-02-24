package cn.tlh.admin.common.pojo;


import lombok.AllArgsConstructor;
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
public class DlxMessage implements Serializable {
    private static final long serialVersionUID = 9956432152000L;

    private String id;
    private String exchange;
    private String queueName;
    private String content;
    private long times;

    public DlxMessage(String id, String queueName, String content, long times) {
        this.id = id;
        this.queueName = queueName;
        this.content = content;
        this.times = times;
    }
}
