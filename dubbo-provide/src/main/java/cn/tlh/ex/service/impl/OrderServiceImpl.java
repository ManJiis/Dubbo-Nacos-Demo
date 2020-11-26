package cn.tlh.ex.service.impl;

import cn.tlh.ex.common.entity.Order;
import cn.tlh.ex.common.vo.req.OrderPageVo;
import cn.tlh.ex.service.OrderService;
import cn.tlh.ex.dao.OrderDao;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * 今日订单(Order)表服务实现类
 *
 * @author makejava
 * @since 2020-11-25 11:22:22
 */
@Service(version = "${service.version}")
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;


    @Override
    public Order queryById(String id) {
        System.out.println("id = " + id);
        return this.orderDao.queryById(id);
    }


    @Override
    public Page<Order> queryList(OrderPageVo orderPageVo) {
        Page<Order> orderPage = new Page<Order>(orderPageVo.getCurrentPage(), orderPageVo.getPageSize());
        return orderDao.queryList(orderPage, orderPageVo);
    }
}