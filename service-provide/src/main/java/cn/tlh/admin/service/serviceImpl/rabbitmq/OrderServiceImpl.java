package cn.tlh.admin.service.serviceImpl.rabbitmq;

import cn.tlh.admin.common.base.vo.req.OrderReqPageVo;
import cn.tlh.admin.common.base.vo.req.OrderReqVo;
import cn.tlh.admin.common.pojo.BrokerMessageLog;
import cn.tlh.admin.common.pojo.DlxMessage;
import cn.tlh.admin.common.pojo.Order;
import cn.tlh.admin.common.util.json.JackJsonUtils;
import cn.tlh.admin.dao.BrokerMessageLogDao;
import cn.tlh.admin.dao.OrderDao;
import cn.tlh.admin.service.OrderService;
import cn.tlh.admin.service.rabbitmq.MqService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.tlh.admin.common.util.BuildDlxMessageUtils.buildOrderDelayDlxMessage;
import static cn.tlh.admin.common.util.constants.RabbitMqConstants.MSG_SENDING;

/**
 * 今日订单(Order)表服务实现类
 *
 * @author TANG
 * @since 2020-11-25 11:22:22
 */
@Service(version = "${service.version}")
@Component
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Resource
    OrderDao orderDao;
    @Resource
    MqService mqService;
    @Resource
    private BrokerMessageLogDao brokerMessageLogDao;

    @Override
    public Order queryById(String id) {
        System.out.println("id = " + id);
        return this.orderDao.queryById(id);
    }

    @Override
    public Page<Order> queryList(OrderReqPageVo orderReqPageVo) {
        Page<Order> orderPage = new Page<Order>(orderReqPageVo.getCurrentPage(), orderReqPageVo.getPageSize());
        return orderDao.queryList(orderPage, orderReqPageVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order addOrder(OrderReqVo orderReqVo) {
        Order order = new Order();
        BeanUtils.copyProperties(orderReqVo, order);
        // 新增订单
        orderDao.insert(order);
        //--------------------- 插入消息记录表数据 start ----------------------------//
        // 插入消息记录表数据
        BrokerMessageLog brokerMessageLog = null;
        DlxMessage dlxMessage = null;
        try {
            brokerMessageLog = BrokerMessageLog
                    .builder()
                    // 消息唯一ID
                    .messageId(order.getId())
                    // 保存消息整体 转为JSON 格式存储入库
                    .message(JackJsonUtils.toJsonString(order))
                    // 设置消息状态为0 表示发送中
                    .status(MSG_SENDING)
                    // 设置消息未确认超时时间窗口为 一分钟
                    .nextRetry(order.getPayTimeStart().plusMinutes(1))
                    .createTime(LocalDateTime.now())
                    .updateTime(LocalDateTime.now()).build();
            dlxMessage = buildOrderDelayDlxMessage(order);
        } catch (JsonProcessingException e) {
            log.error("新增订单  构建BrokerMessageLog对象或者DlxMessage对象  JackJsonUtils.toJsonString(order) 转换异常: {}", e.getMessage());
        }
        brokerMessageLogDao.addDeliveryRecord(brokerMessageLog);
        //--------------------- 插入消息记录表数据 end ----------------------------//
        // 发消息  默认1min
        mqService.sendDelayOrder(dlxMessage);
        log.info("发送消息: orderId = [{}]", order.getId());
        return order;
    }
}