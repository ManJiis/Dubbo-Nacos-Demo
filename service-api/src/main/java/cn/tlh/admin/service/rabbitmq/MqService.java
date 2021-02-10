package cn.tlh.admin.service.rabbitmq;

/**
 * @author TANG
 */
public interface MqService {
    /**
     * 正常发送
     *
     * @param queueName queueName
     * @param msg msg
     */
    void send(String queueName, Object msg);

    /**
     * 延时发送
     *
     * @param queueName 队列名称
     * @param msg       消息
     * @param times     延时时间，单位毫秒
     */
    void sendDelay(String queueName, String msg, Integer times);
}
