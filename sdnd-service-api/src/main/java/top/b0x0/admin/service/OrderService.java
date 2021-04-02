package top.b0x0.admin.service;

import top.b0x0.admin.common.vo.req.OrderReqPageVo;
import top.b0x0.admin.common.vo.req.OrderReqVo;
import top.b0x0.admin.common.pojo.Order;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 今日订单(Order)表服务接口
 *
 * @author TANG
 * @since 2020-11-25
 */
public interface OrderService {

    Order queryById(String id);

    Page<Order> queryList(OrderReqPageVo orderReqPageVo);

    Order addOrder(OrderReqVo orderReqVo);

}