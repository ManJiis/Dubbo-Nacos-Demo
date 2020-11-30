package cn.tlh.ex.controller;

import cn.tlh.ex.common.entity.Order;
import cn.tlh.ex.common.vo.req.OrderPageVo;
import cn.tlh.ex.common.vo.resp.ResultInfo;
import cn.tlh.ex.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 今日订单(TOrder)表控制层
 *
 * @author makejava
 * @since 2020-11-25 11:44:03
 */
@RestController
@RequestMapping("order")
@Api(tags = "订单服务")
public class OrderController {
    /**
     * 服务对象
     */
    @Reference(version = "${service.version}",check = false)
    private OrderService orderService;


    @GetMapping("one")
    public ResultInfo selectOne(String id) {
        Order order = this.orderService.queryById(id);
        ResultInfo<Order> resultInfo = new ResultInfo();
        resultInfo.setData(order);
        return resultInfo;
    }

    @GetMapping("list")
    public ResultInfo selectList(OrderPageVo orderPageVo) {
        IPage<Order> orderIPage = this.orderService.queryList(orderPageVo);
        ResultInfo<IPage> resultInfo = new ResultInfo();
        resultInfo.setData(orderIPage);
        return resultInfo;
    }

}