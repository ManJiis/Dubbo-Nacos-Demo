package cn.tlh.common.util;

/**
 * @author: ling
 * @description: 常量类
 * @date: 2020-12-05
 * @version: 1.0
 */
public class Constant {
    /**
     * 发送中
     */
    public static final String ORDER_SENDING = "0";

    /**
     * 成功
     */
    public static final String ORDER_SEND_SUCCESS = "1";

    /**
     * 失败
     */
    public static final String ORDER_SEND_FAILURE = "-1";
    /**
     * 分钟超时单位：min
     */
    public static final int ORDER_TIMEOUT = 1;

    /**
     * exception
     */
    public static final String BAD_REQUEST_MSG = "客户端请求参数错误";

    //-------------------------------- 订单延迟队列 start --------------------------------//
    /**
     * 订单延迟队列
     */
    public static final String ORDER_DELAY_QUEUE = "dev.order.queue";
    /**
     * 订单exchange
     */
    public static final String ORDER_EXCHANGE = "dev.order.exchange";
    /**
     * 死信交换机
     */
    public static final String ORDER_DLX_EXCHANGE = "dev.order.dlx.exchange";
    /**
     * 超时订单任务队列
     */
    public static final String ORDER_TIMEOUT_QUEUE = "dev.order.timeout.queue";
    /**
     * 过期消息路由key
     */
    public static final String ORDER_DLK_KEY = "dev.order.delay";
    //-------------------------------- 订单延迟队列 end --------------------------------//


}
