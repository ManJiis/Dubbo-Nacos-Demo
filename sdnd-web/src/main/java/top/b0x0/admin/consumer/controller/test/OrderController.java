package top.b0x0.admin.consumer.controller.test;

import top.b0x0.admin.common.vo.R;
import top.b0x0.admin.common.vo.req.OrderReqPageVo;
import top.b0x0.admin.common.vo.req.OrderReqVo;
import top.b0x0.admin.common.pojo.Order;
import top.b0x0.admin.common.util.id.IdUtils;
import top.b0x0.admin.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @author TANG
 * @since 2020-11-25 11:44:03
 */
@RestController
@RequestMapping("order")
@Api(tags = "测试：订单服务", value = "mq延迟队列示例")
public class OrderController {


    @Reference(version = "${service.version}", check = false)
    private OrderService orderService;


    @GetMapping("one")
    public R selectOne(String id) {
        Order order = this.orderService.queryById(id);
        return R.ok(order);
    }

    @GetMapping("list")
    public R selectList(OrderReqPageVo orderReqPageVo) {
        IPage<Order> orderPage = this.orderService.queryList(orderReqPageVo);
        return R.ok(orderPage);
    }

    @GetMapping("add")
    @ApiOperation("新增订单 MQ延时消息测试")
    public R add() {
        OrderReqVo vo = new OrderReqVo();
        vo.setId(IdUtils.snowflakeId().toString());
        vo.setOrderSource(0);
        // 交易状态 0:待支付 1:交易成功 2:交易失败
        vo.setState(0);
        vo.setAmount(new BigDecimal("56"));
        vo.setCardNo("15680764567");
        vo.setMerNo("4301980876461234");
        vo.setMerName("杭州小笼包");
        vo.setBody("进货面粉");
        vo.setPayTimeStart(LocalDateTime.now());
        vo.setCreateTime(LocalDate.now());
        return R.ok(this.orderService.addOrder(vo));
    }

}