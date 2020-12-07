package cn.tlh.ex.consumer.controller.order;

import cn.tlh.ex.common.entity.Order;
import cn.tlh.ex.common.util.SnowFlakeUtil;
import cn.tlh.ex.common.vo.req.OrderPageVo;
import cn.tlh.ex.common.vo.req.OrderVo;
import cn.tlh.ex.common.vo.resp.Response;
import cn.tlh.ex.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


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

    @GetMapping("add")
    public Response add() {
        OrderVo vo = new OrderVo();
        vo.setId(SnowFlakeUtil.createSnowflakeId().toString());
        vo.setOrderSource(0);
        // 交易状态 0:待支付 1:交易成功 2:交易失败
        vo.setState(0);
        vo.setAmount(new BigDecimal("56"));
        vo.setCardNo("15680764567");
        vo.setMerNo("4301980876461234");
        vo.setMerName("杭州小笼包");
        vo.setBody("进货面粉");
        vo.setPayTimeStart(LocalDateTime.now());
        vo.setPayTimeEnd(LocalDateTime.now().plusMinutes(1));
        vo.setFinishTime(LocalDateTime.now().plusMinutes(1));
        vo.setCreateTime(LocalDate.now());
        String add = this.orderService.add(vo);
        return Response.ok(add);
    }

}