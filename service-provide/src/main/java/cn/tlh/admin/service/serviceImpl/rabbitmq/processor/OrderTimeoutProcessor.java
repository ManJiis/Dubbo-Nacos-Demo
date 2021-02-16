package cn.tlh.admin.service.serviceImpl.rabbitmq.processor;


import cn.tlh.admin.common.pojo.DlxMessage;
import cn.tlh.admin.common.pojo.Order;
import cn.tlh.admin.common.util.constant.RabbitMqConstants;
import cn.tlh.admin.dao.OrderDao;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

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

    @RabbitHandler
    @RabbitListener(queues = RabbitMqConstants.ORDER_TIMEOUT_QUEUE)
    public void process(String content, Channel channel, Message messages) {
        try {
            DlxMessage message = JSON.parseObject(content, DlxMessage.class);
            Order order = new Order();
            // 交易状态 0:待支付 1:交易成功 2:交易失败
            order.setState(2);
            order.setErrorMsg("取消订单");
            UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
            orderDao.update(order, updateWrapper.eq("id", message.getContent()));
            System.out.println("超时订单 订单号: [" + message.getContent() + "] 取消完毕......");
            /*
             *  第二个参数取值为 false 时，表示通知 RabbitMQ 当前消息被确认
             *  如果为 true，则额外将比第一个参数指定的 delivery tag小的消息一并确认
             */
            channel.basicAck(messages.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
