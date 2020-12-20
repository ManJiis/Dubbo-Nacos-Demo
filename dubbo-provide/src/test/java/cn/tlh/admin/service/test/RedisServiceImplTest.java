package cn.tlh.admin.service.test;

import cn.tlh.ProvideApplication;
import cn.tlh.admin.dao.OrderDao;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProvideApplication.class)
public class RedisServiceImplTest {

    @Resource
    OrderDao orderDao;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void testRedis1() {
        Long increment = stringRedisTemplate.opsForValue().increment("202008091746251021001639329", 2);
        System.out.println("increment = " + increment);

    }

    @Test
    public void testRedis2() {
        Date date = new Date();
        long l = date.getTime();
        System.out.println("dateTime = " + l);

        // 1606704110023

        long dateTime = 1606704110023L;
        Date date1 = new Date(dateTime);
        System.out.println(date1.toString());
    }

    @Test
    public void test3() {
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        List<String> list2 = new ArrayList<>();
        list2.add("2");
        list2.add("3");
        list2.add("4");
/*        for (String value : list1) {
            for (String s : list2) {
                if (value.equals(s)) {
                    System.out.println("重复元素 = " + s);
                }
            }
        }*/
        System.out.println("Collections.disjoint(list1,list2) = " + Collections.disjoint(list1, list2));
    }

    @Test
    public void test4() throws ParseException {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = "2021-01-28";
        Date parse1 = format1.parse(date1);
        System.out.println("parse1 = " + parse1);
        String date2 = "2020-12-10 15:05:25";
        Date parse2 = format2.parse(date2);
        System.out.println("parse2 = " + parse2);
    }

    @Data
    @Builder
    static class Dept {
        private String id;
        private Boolean enable;
    }

    @Test
    public void test5() {
        Dept dept1 = new Dept("0755-0001", true);
        Dept dept2 = new Dept("0755-0001", false);
        List<Dept> deptList = new ArrayList<>();
        for (Dept dept : deptList) {
            System.out.println("(dept.getEnable() ? \"启用\" : \"停用\") = " + (dept.getEnable() ? "启用" : "停用"));
        }
    }
}




