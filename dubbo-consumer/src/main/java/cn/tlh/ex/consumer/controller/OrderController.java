package cn.tlh.ex.consumer.controller;

import cn.tlh.ex.common.entity.Order;
import cn.tlh.ex.common.vo.req.OrderPageVo;
import cn.tlh.ex.common.vo.resp.Response;
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
    @Reference(version = "${service.version}", check = false)
    private OrderService orderService;


    @GetMapping("one")
    public Response selectOne(String id) {
        Order order = this.orderService.queryById(id);
        return Response.ok(order);
    }

    @GetMapping("list")
    public Response selectList(OrderPageVo orderPageVo) {
        IPage<Order> orderIPage = this.orderService.queryList(orderPageVo);
        return Response.ok(orderIPage);
    }

}