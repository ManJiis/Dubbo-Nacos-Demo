package cn.tlh.admin.service.serviceImpl.example;

import cn.tlh.admin.common.base.vo.req.OrderReqPageVo;
import cn.tlh.admin.common.base.vo.req.OrderReqVo;
import cn.tlh.admin.common.pojo.BrokerMessageLog;
import cn.tlh.admin.common.pojo.Order;
import cn.tlh.admin.dao.BrokerMessageLogDao;
import cn.tlh.admin.dao.OrderDao;
import cn.tlh.admin.service.OrderService;
import cn.tlh.admin.service.mq.MqService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.tlh.admin.common.util.constant.AdminConstants.ORDER_DELAY_QUEUE;

/**
 * 今日订单(Order)表服务实现类
 *
 * @author TANG
 * @since 2020-11-25 11:22:22
 */
@Service(version = "${service.version}")
@Component
public class OrderServiceImpl implements OrderService {

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
    public String add(OrderReqVo orderReqVo) {
        Order order = new Order();
        BeanUtils.copyProperties(orderReqVo, order);
        // 新增数据
        orderDao.insert(order);
        //--------------------- 插入消息记录表数据 start ----------------------------//
        // 插入消息记录表数据
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        // 消息唯一ID
        brokerMessageLog.setMessageId(order.getId());
        // 保存消息整体 转为JSON 格式存储入库
        brokerMessageLog.setMessage(JSON.toJSONString(order));
        // 设置消息状态为0 表示发送中
        brokerMessageLog.setStatus("0");
        // 设置消息未确认超时时间窗口为 一分钟
        brokerMessageLog.setNextRetry(order.getPayTimeStart().plusMinutes(1));
        brokerMessageLog.setCreateTime(LocalDateTime.now());
        brokerMessageLog.setUpdateTime(LocalDateTime.now());
        brokerMessageLogDao.insertSelective(brokerMessageLog);
        //--------------------- 插入消息记录表数据 end ----------------------------//
        // 发消息
        mqService.sendDelay(ORDER_DELAY_QUEUE, order.getId(), 1000 * 3);
        System.out.println("发送消息成功: orderId = [" + order.getId() + "]");
        return order.getId();
    }
}