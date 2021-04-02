
package top.b0x0.admin.consumer.controller.system;

import top.b0x0.admin.common.vo.BusinessResponse;
import top.b0x0.admin.common.util.DateUtils;
import top.b0x0.admin.consumer.annotaion.AccessLimit;
import top.b0x0.admin.consumer.annotaion.LimitType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

//import cn.tlh.provide.aop.annotaion.rest.AnonymousGetMapping;

/**
 * @author TANG
 * 接口限流测试类
 */
@RestController
@RequestMapping("system/limit")
@Api(tags = "系统：限流测试管理")
public class LimitController {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    private static final Logger log = LoggerFactory.getLogger(LimitController.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试限流注解，下面配置说明该接口 60秒内最多只能访问 10次，保存到redis的键名为 limit_test，
     */
    @ApiOperation("限流测试 -- 方式: 默认")
    @AccessLimit(description = "system/limit", keyPrefix = "api_limit", period = 60, count = 10)
    @GetMapping("/customer")
    public BusinessResponse limitByCustomer() {
        String date = DateUtils.localDateTimeFormatyMdHms(LocalDateTime.now());
        RedisAtomicInteger limitCounter = new RedisAtomicInteger("limitCounterByCustomer", Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        int incrementAndGet = limitCounter.incrementAndGet();
        log.info("------------>> 当前时间: " + date + " 累计访问次数：" + incrementAndGet);
        return BusinessResponse.ok("当前时间: " + date + " 接口累计访问次数：" + incrementAndGet);
    }

    @ApiOperation("限流测试 -- 方式: IP")
    @AccessLimit(description = "system/limit", keyPrefix = "api_limit", period = 60, count = 10, limitType = LimitType.IP)
    @GetMapping("/ip")
    public BusinessResponse limitByIp() {
        String date = DateUtils.localDateTimeFormatyMdHms(LocalDateTime.now());
        RedisAtomicInteger limitCounter = new RedisAtomicInteger("limitCounterByIp", Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        int incrementAndGet = limitCounter.incrementAndGet();
        log.info("------------>> 当前时间: " + date + " 累计访问次数：" + incrementAndGet);
        return BusinessResponse.ok("当前时间: " + date + " 接口累计访问次数：" + incrementAndGet);
    }
}
