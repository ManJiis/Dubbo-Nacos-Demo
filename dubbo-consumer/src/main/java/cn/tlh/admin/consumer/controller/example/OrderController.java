package cn.tlh.admin.consumer.controller.example;

import cn.tlh.admin.common.pojo.Order;
import cn.tlh.admin.common.util.SnowFlakeUtil;
import cn.tlh.admin.common.base.vo.req.OrderReqPageVo;
import cn.tlh.admin.common.base.vo.req.OrderReqVo;
import cn.tlh.admin.common.base.vo.BusinessResponse;
import cn.tlh.admin.service.OrderService;
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
 * @author TANG
 * @since 2020-11-25 11:44:03
 */
@RestController
@RequestMapping("order")
@Api(tags = "测试：订单服务",value = "mq延迟队列示例")
public class OrderController {
    /**
     * 服务对象
     */
    @Reference(version = "${service.version}", check = false)
    private OrderService orderService;


    @GetMapping("one")
    public BusinessResponse selectOne(String id) {
        Order order = this.orderService.queryById(id);
        return BusinessResponse.ok(order);
    }

    @GetMapping("list")
    public BusinessResponse selectList(OrderReqPageVo orderReqPageVo) {
        IPage<Order> orderPage = this.orderService.queryList(orderReqPageVo);
        return BusinessResponse.ok(orderPage);
    }

    @GetMapping("add")
    public BusinessResponse add() {
        OrderReqVo vo = new OrderReqVo();
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
        return BusinessResponse.ok(add);
    }

}