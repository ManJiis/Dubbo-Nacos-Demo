package top.b0x0.admin.dao;

import top.b0x0.admin.common.vo.req.OrderReqPageVo;
import top.b0x0.admin.common.pojo.Order;
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

    Page<Order> queryList(Page<Order> page, @Param("opv") OrderReqPageVo orderReqPageVo);

    String findMaxId();
}