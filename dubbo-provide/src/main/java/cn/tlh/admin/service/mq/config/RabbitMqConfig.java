package cn.tlh.admin.service.mq.config;

import cn.tlh.admin.common.util.constant.AdminConstants;
import cn.tlh.admin.dao.BrokerMessageLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: TANG
 * @description: mq配置 ACK
 * @date: 2020-12-6
 **/
//@Configuration
public class RabbitMqConfig {
    private final Logger logger = LoggerFactory.getLogger(RabbitMqConfig.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private BrokerMessageLogDao brokerMessageLogDao;

    @Bean
    public AmqpTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);
        // 1. ConfirmCallback 消息发送确认  >>确认发送到交换机
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.err.println("correlationData: " + correlationData);
            String messageId = correlationData.getId();
            //成功发送到交换机会触发该回调
            if (ack) {
                //如果confirm返回成功 则进行更新消息投递状态
                brokerMessageLogDao.changeBrokerMessageLogStatus(messageId, AdminConstants.ORDER_SEND_SUCCESS, new Date());
            } else {
                //表示消息成功发送到服务器，但是没有找到交换器，这里可以记录日志，方便后续处理
                logger.warn("ConfirmCallback -> 消息发布到交换器失败，错误原因为：{}", cause);
            }
        });
        // 2. ReturnCallback 消息接收确认 >>消费者确认收到该消息
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            //表示消息发送到交换机，但是没有找到队列，这里记录日志
            logger.warn(
                    "ReturnCallback -> 消息{}，发送到队列失败，应答码：{}，原因：{}，交换器: {}，路由键：{}",
                    message, replyCode, replyText, exchange, routingKey);
        });
        return rabbitTemplate;
    }
}
