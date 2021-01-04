
package cn.tlh.admin.consumer.controller.system;

import cn.tlh.admin.common.util.DateUtil;
import cn.tlh.admin.consumer.aop.annotaion.Limit;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(LimitController.class);

    @Autowired(required = false)
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试限流注解，下面配置说明该接口 60秒内最多只能访问 10次，保存到redis的键名为 limit_test，
     */
    @ApiOperation("测试")
    @Limit(description = "testLimit", prefix = "limit", key = "test", period = 60, count = 10)
    @GetMapping
    public int test() {
        String date = DateUtil.localDateTimeFormatyMdHms(LocalDateTime.now());
        RedisAtomicInteger limitCounter = new RedisAtomicInteger("limitCounter", Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        System.out.println(date + " 累计访问次数：" + limitCounter.getAndIncrement());
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
