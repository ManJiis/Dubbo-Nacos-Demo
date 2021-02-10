package cn.tlh.admin.service.serviceImpl.rabbitmq;

import cn.tlh.admin.common.pojo.DlxMessage;
import cn.tlh.admin.common.util.constant.AdminConstants;
import cn.tlh.admin.common.util.id.SnowFlakeUtil;
import cn.tlh.admin.dao.BrokerMessageLogDao;
import cn.tlh.admin.service.rabbitmq.MqService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;


/**
 * mq发消息
 *
 * @author TANG
 */
@Service(version = "${service.version}")
@Component
public class MqServiceImpl implements MqService, RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private static final Logger logger = LoggerFactory.getLogger(MqServiceImpl.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Resource
    private BrokerMessageLogDao brokerMessageLogDao;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息唯一标识：" + correlationData);
        System.out.println("确认结果：" + ack);
        System.out.println("失败原因：" + cause);
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
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息主体 message : " + message);
        System.out.println("消息主体 message : " + replyCode);
        System.out.println("描述：" + replyText);
        System.out.println("消息使用的交换器 exchange : " + exchange);
        System.out.println("消息使用的路由键 routing : " + routingKey);
        //表示消息发送到交换机，但是没有找到队列，这里记录日志
        logger.warn(
                "ReturnCallback -> 消息{}，发送到队列失败，应答码：{}，原因：{}，交换器: {}，路由键：{}",
                message, replyCode, replyText, exchange, routingKey);
    }

    @Override
    public void send(String queueName, Object msg) {
        String msgId = SnowFlakeUtil.createSnowflakeId().toString();
        logger.info("send msg，队列名：{}，消息： {}，CorrelationData：{}", queueName, msg, msgId);
        this.rabbitTemplate.convertAndSend(queueName, msg, new CorrelationData(msgId));
    }

    @Override
    public void sendDelay(String queueName, String msg, Integer times) {
        //---------------------- ACK ------------------------//
        //设置返回回调
        rabbitTemplate.setReturnCallback(this);
        //设置确认回调
        rabbitTemplate.setConfirmCallback(this);
        //---------------------- ACK ------------------------//

        DlxMessage dlxMessage = new DlxMessage(queueName, msg, times);
        MessagePostProcessor processor = (message) -> {
//            message.getMessageProperties().setDelay(times);
            message.getMessageProperties().setExpiration(String.valueOf(times));
            return message;
        };
        // 消息的投递方式默认为2（持久化） 看源码，Message的封装
        rabbitTemplate.convertAndSend(
                AdminConstants.ORDER_EXCHANGE,
                AdminConstants.ORDER_DLK_KEY,
                dlxMessage,
                processor);
        logger.info("延时队列发送：队列名：{}，消息：{}，延时时间（毫秒）：{}", queueName, msg, times);
    }


}
