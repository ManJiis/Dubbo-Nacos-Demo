package top.b0x0.admin.service.rabbitmq.processor;


import top.b0x0.admin.common.pojo.DlxMessage;
import top.b0x0.admin.common.pojo.Order;
import top.b0x0.admin.common.util.constants.RabbitMqConstants;
import top.b0x0.admin.dao.OrderDao;
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
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 消费订单延迟队列中的消息
 *
 * @author TANG
 */
@Component
public class OrderProcessor {

    private static final Logger log = LoggerFactory.getLogger(OrderProcessor.class);

    @Resource
    OrderDao orderDao;

    /**
     * 订单确认支付
     *
     * @param message /
     * @param channel /
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConstants.ORDER_DELAY_QUEUE)
    public void confirmPaymentProcess(Message<DlxMessage> message, Channel channel) {
        MessageHeaders headers = message.getHeaders();
        try {
            DlxMessage dlxMessage = message.getPayload();
            log.info("======DlxMessage: {}", dlxMessage);
            Order order = new Order();
            // 交易状态 0:待支付 1:交易成功 2:交易失败
            order.setState(1);
            order.setPayTimeEnd(LocalDateTime.now());
            order.setFinishTime(LocalDateTime.now());
            UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();

//           int i = 1 / 0;
            String messageId = Objects.requireNonNull(dlxMessage).getMessageId();
            orderDao.update(order, updateWrapper.eq("id", messageId));

            log.info("确认支付 订单id: [{}] 订单信息: {} 已支付......", messageId, order);

            int i = 1 / 0;

            /*
             *  consumer处理成功后，通知broker删除队列中的消息
             *  第二个参数取值为 false 时，表示通知 RabbitMQ 当前消息被确认
             *  如果为 true，则额外将比第一个参数指定的 delivery tag小的消息一并确认, 表示支持批量确认机制以减少网络流量
             */
            log.info("amqp_deliveryTag : {}", headers.get("amqp_deliveryTag"));
            channel.basicAck(Long.parseLong(Objects.requireNonNull(headers.get("amqp_deliveryTag")).toString()), false);
        } catch (Exception e) {
            log.error("======confirmPaymentProcess  error: {}", e.getMessage());
            log.error("message: {}", message);
        }
    }

    /**
     * 处理死信消息
     * 订单在存活时间内没有被消费 会被转发到 RabbitMqConstants.ORDER_TIMEOUT_QUEUE队列中
     *
     * @param message /
     * @param channel /
     */
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConstants.ORDER_TIMEOUT_QUEUE)
    public void timeOutOrderProcess(Message<DlxMessage> message, Channel channel) {
        MessageHeaders headers = message.getHeaders();
        try {
            DlxMessage dlxMessage = message.getPayload();
            log.info("======DlxMessage: {}", dlxMessage);
            Order order = new Order();
            // 交易状态 0:待支付 1:交易成功 2:交易失败
            order.setState(2);
            order.setErrorMsg("规定时间内未支付, 取消订单");
            order.setFinishTime(LocalDateTime.now());
            UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
            String messageId = Objects.requireNonNull(dlxMessage).getMessageId();
            orderDao.update(order, updateWrapper.eq("id", messageId));
            log.info("取消订单 订单id: [{}] 取消完毕......", dlxMessage.getMessageId());

            log.info("amqp_deliveryTag : {}", headers.get("amqp_deliveryTag"));
            /*
             *  第二个参数取值为 false 时，表示通知 RabbitMQ 当前消息被确认
             *  如果为 true，则额外将比第一个参数指定的 delivery tag小的消息一并确认
             */
            channel.basicAck(Long.parseLong(Objects.requireNonNull(headers.get("amqp_deliveryTag")).toString()), false);
        } catch (Exception e) {
            log.error("======timeOutOrderProcess  error: {}", e.getMessage());
            log.error("message: {}", message);
        }
    }
    /*
     * 问题:
     * channel.basicAck 之前出异常了 消息变成Unacked 并且等消息过期后 重启服务 会变成死信被重新消费
     */
}
