package cn.tlh.ex.test.service.impl;

import cn.tlh.ProvideApplication;
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
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProvideApplication.class)
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

    @Test
    public void testRedis7() {
        Date date = new Date();
        long l = date.getTime();
        System.out.println("dateTime = " + l);

        // 1606704110023

        long dateTime = 1606704110023L;
        Date date1 = new Date(dateTime);
        System.out.println(date1.toString());
    }

    @Test
    public void testRedis8() {
        List<Map> mapList = new ArrayList<>();
        Map map1 = new HashMap<String, String>();
        map1.put("longitude", "10.23456");
        map1.put("latitude", "10.23456");
        Map map2 = new HashMap<String, String>();
        map2.put("longitude", "10.23457");
        map2.put("latitude", "10.23457");
        Map map3 = new HashMap<String, String>();
        map3.put("longitude", "10.23458");
        map3.put("latitude", "10.23459");
        mapList.add(map1);
        mapList.add(map2);
        mapList.add(map3);
        System.out.println("mapList = " + mapList);
        for (int i = 0; i < mapList.size(); i++) {
            redisService.setList2RightPush("coordinatedata", mapList.get(i).toString());
//            List list = redisTemplate.opsForList().range("coordinatedata", 0, mapList.size() - 1);
        }
    }

    @Test
    public void testRedis9() {

        List<String> coordinatedata = redisService.getList("coordinatedata", 0, 1);
        System.out.println("coordinatedata = " + coordinatedata);

    }    @Test
    public void testRedis10() {

        boolean coordinatedata = redisService.hasKey("coordinatedata");
        System.out.println("coordinatedata = " + coordinatedata);

    }
}




