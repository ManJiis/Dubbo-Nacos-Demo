package cn.tlh.admin.dao;

import cn.tlh.admin.common.pojo.Order;
import cn.tlh.admin.common.base.vo.req.OrderPageVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;


/**
 * 今日订单(Order)表数据库访问层
 *
 * @author TANG
 * @since 2020-11-25 10:31:36
 */
public interface OrderDao extends BaseMapper<Order> {

    Order queryById(String id);

    Page<Order> queryList(Page<Order> page, @Param("opv") OrderPageVo orderPageVo);

    String findMaxId();
}