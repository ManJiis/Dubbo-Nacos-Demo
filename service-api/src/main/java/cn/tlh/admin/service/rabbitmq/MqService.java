package cn.tlh.admin.service.rabbitmq;

import cn.tlh.admin.common.pojo.DlxMessage;

/**
 * @author TANG
 */
public interface MqService {
    /**
     * 正常发送
     *
     * @param messageId 消息唯一Id  IdUtils.snowflakeId().toString()
     * @param queueName queueName
     * @param message   msg
     */
    void send(String messageId, String queueName, Object message);

    /**
     * 延时消息
     *
     * @param dlxMessage 死信消息载体
     */
    void sendDelayOrder(DlxMessage dlxMessage);
}
