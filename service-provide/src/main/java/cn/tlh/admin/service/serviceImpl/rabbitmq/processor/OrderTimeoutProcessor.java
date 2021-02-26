package cn.tlh.admin.service.serviceImpl.rabbitmq.processor;


import cn.tlh.admin.common.pojo.DlxMessage;
import cn.tlh.admin.common.pojo.Order;
import cn.tlh.admin.common.util.constants.RabbitMqConstants;
import cn.tlh.admin.dao.OrderDao;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 消费订单延迟队列中的消息
 *
 * @author TANG
 */
@Component
public class OrderTimeoutProcessor {

    private static final Logger log = LoggerFactory.getLogger(OrderTimeoutProcessor.class);

    @Resource
    OrderDao orderDao;

    /**
     * 处理死信消息
     *
     * @param message /
     * @param channel /
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConstants.ORDER_TIMEOUT_QUEUE)
    public void process(Message<DlxMessage> message, Channel channel) {
        MessageHeaders headers = message.getHeaders();
        try {
            log.info("======当前时间: {}, OrderTimeoutProcessor  message: {}", LocalDateTime.now(), message);
            DlxMessage dlxMessage = message.getPayload();
            Order order = new Order();
            // 交易状态 0:待支付 1:交易成功 2:交易失败
            order.setState(2);
            order.setErrorMsg("取消订单");
            UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
            orderDao.update(order, updateWrapper.eq("id", Objects.requireNonNull(dlxMessage).getMessage()));
            log.info("超时订单 订单号: [{}] 取消完毕......", dlxMessage.getMessage());

            log.info("amqp_deliveryTag : {}", headers.get("amqp_deliveryTag"));
            /*
             *  第二个参数取值为 false 时，表示通知 RabbitMQ 当前消息被确认
             *  如果为 true，则额外将比第一个参数指定的 delivery tag小的消息一并确认
             */
            channel.basicAck(Long.parseLong(Objects.requireNonNull(headers.get("amqp_deliveryTag")).toString()), false);
        } catch (IOException e) {
            log.error("======当前时间: {}, OrderTimeoutProcessor  message: {}", LocalDateTime.now(), message);
            log.error("amqp_deliveryTag : {}", headers.get("amqp_deliveryTag"));
            log.error("OrderTimeout Processor error: {}", e.getMessage());
        }
    }
}
