package cn.tlh.ex.service;

import cn.tlh.ex.common.entity.Order;
import cn.tlh.ex.common.vo.req.OrderPageVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 今日订单(Order)表服务接口
 *
 * @author makejava
 * @since 2020-11-25 11:13:51
 */
public interface OrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Order queryById(String id);

    Page<Order> queryList(OrderPageVo orderPageVo);


}