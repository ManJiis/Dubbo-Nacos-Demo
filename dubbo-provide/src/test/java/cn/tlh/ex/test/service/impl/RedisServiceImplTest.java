package cn.tlh.ex.test.service.impl;

import cn.tlh.DubboProvideApplication;
import cn.tlh.ex.common.entity.Order;
import cn.tlh.ex.dao.OrderDao;
import cn.tlh.ex.service.RedisService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DubboProvideApplication.class)
public class RedisServiceImplTest {

    @Resource
    private RedisService redisService;
    @Resource
    OrderDao orderDao;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void testRedis1() throws IOException {
        // 202008091746251021001639329
        Order order = orderDao.queryById("202008091746251021001639329");
        if (order == null) {
            order = new Order();
            order.setId("11111111111");
        }
        redisService.setString("order", order);
        System.out.println("redisService.getString(\"order\") = " + redisService.getString("order"));
    }

    @Test
    public void testRedis2() {
        String maxId = orderDao.findMaxId();
        System.out.println("maxId = " + maxId);
        String increment = redisService.increment(maxId);
        System.out.println("increment = " + increment);

    }

    @Test
    public void testRedis3() {
        Long increment = stringRedisTemplate.opsForValue().increment("202008091746251021001639329", 2);
        System.out.println("increment = " + increment);

    }


    @Test
    public void testRedis5() {
        boolean order = redisService.setString("20201127", "order", 30);
        System.out.println("order = " + order);
    }

    @Test
    public void testRedis6() {
        redisService.setList2LeftPush("orderIdList", "1111");
        redisService.setList2LeftPush("orderIdList", "2222");
        redisService.setList2RightPush("orderIdList", "3333");
    }
}




