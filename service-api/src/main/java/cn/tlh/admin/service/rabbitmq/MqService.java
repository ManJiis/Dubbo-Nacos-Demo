package cn.tlh.admin.service.rabbitmq;

/**
 * @author TANG
 */
public interface MqService {
    /**
     * 正常发送
     *  @param messageId 消息唯一Id  IdUtils.snowflakeId().toString()
     * @param queueName queueName
     * @param message   msg
     */
    void send( String messageId,String queueName, Object message);

    /**
     * 延时发送
     *
     * @param messageId  消息唯一Id  IdUtils.snowflakeId().toString()
     * @param queueName 队列名称
     * @param message   消息
     * @param times     延时时间，单位毫秒
     */
    void sendDelayOrder(String messageId, String queueName, String message, Integer times);
}
