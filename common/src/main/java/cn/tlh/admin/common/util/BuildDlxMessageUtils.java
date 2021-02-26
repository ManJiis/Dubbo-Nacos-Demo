package cn.tlh.admin.common.util;

import cn.tlh.admin.common.pojo.DlxMessage;
import cn.tlh.admin.common.pojo.Order;
import cn.tlh.admin.common.util.constants.RabbitMqConstants;
import cn.tlh.admin.common.util.json.JackJsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 构建死信消息实例
 *
 * @author TANG
 * @since 2021-02-26
 */
public class BuildDlxMessageUtils {

    /**
     * 构建延时订单消息
     *
     * @param order          /
     * @param expirationTime 过期时间 单位毫秒
     * @return DlxMessage
     */
    public static DlxMessage buildOrderDelayDlxMessage(Order order, Long expirationTime) throws JsonProcessingException {
        return DlxMessage.builder()
                .messageId(order.getId())
                .message(JackJsonUtils.toJsonString(order))
                .queue(RabbitMqConstants.ORDER_DELAY_QUEUE)
                .exchange(RabbitMqConstants.ORDER_EXCHANGE)
                .routingKey(RabbitMqConstants.ORDER_DLK_KEY).expiration(expirationTime == null ? 1000 * 60L : expirationTime).build();
    }

    /**
     * 构建延时订单消息 默认延时一分钟
     *
     * @param order /
     * @return DlxMessage
     */
    public static DlxMessage buildOrderDelayDlxMessage(Order order) throws JsonProcessingException {
        return DlxMessage.builder()
                .messageId(order.getId())
                .message(JackJsonUtils.toJsonString(order))
                .queue(RabbitMqConstants.ORDER_DELAY_QUEUE)
                .exchange(RabbitMqConstants.ORDER_EXCHANGE)
                .routingKey(RabbitMqConstants.ORDER_DLK_KEY).expiration(1000 * 60L).build();
    }
}
