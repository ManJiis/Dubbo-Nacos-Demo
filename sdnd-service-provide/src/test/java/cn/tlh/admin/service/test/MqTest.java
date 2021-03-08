package cn.tlh.admin.service.test;

import cn.tlh.admin.common.base.vo.req.OrderReqVo;
import cn.tlh.admin.common.util.id.IdUtils;
import cn.tlh.admin.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * mq消息确认机制测试
 *
 * @author TANG
 * @date 2021-02-24
 */
@SpringBootTest
public class MqTest {

    @Reference(version = "${service.version}", check = false)
    private OrderService orderService;

    @Test
    public void test() {
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
        vo.setPayTimeEnd(LocalDateTime.now().plusMinutes(1));
        vo.setFinishTime(LocalDateTime.now().plusMinutes(1));
        vo.setCreateTime(LocalDate.now());
        orderService.addOrder(vo);
    }
}
