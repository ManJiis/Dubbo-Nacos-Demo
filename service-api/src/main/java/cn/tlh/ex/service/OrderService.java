package cn.tlh.ex.service;

import cn.tlh.ex.common.entity.Order;
import cn.tlh.ex.common.vo.req.OrderPageVo;
import cn.tlh.ex.common.vo.req.OrderVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 今日订单(Order)表服务接口
 *
 * @author makejava
 * @since 2020-11-25
 */
public interface OrderService {

    Order queryById(String id);

    Page<Order> queryList(OrderPageVo orderPageVo);


    String add(OrderVo orderVo);
}