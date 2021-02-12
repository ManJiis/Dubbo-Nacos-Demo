package cn.tlh.admin.service.test;

import cn.tlh.admin.service.serviceImpl.ProvideApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author TANG
 * @description 分布式锁测试
 * @date 2021-02-12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProvideApplication.class)
public class RedissionTest {

    private static final Logger log = LoggerFactory.getLogger(RedissionTest.class);

    @Resource
    private RedissonClient redissonClient;

    @Test
    public void redissionTest1() {
        RBucket<String> key = redissonClient.getBucket("newday");
        System.out.println("获取到的数据：" + key.get());
    }

    @Test
    public void redissionTest2() {
        // 获取字符串格式的数据
        RBucket<String> keyObj = redissonClient.getBucket("myname");
        System.out.println("1 --> 获取到的数据：" + keyObj.get());
        long currentTimeMillis = System.currentTimeMillis();
        keyObj.set(currentTimeMillis + "");
        System.out.println("2 --> 设置的数据：" + currentTimeMillis);
        System.out.println("3 --> 获取到的数据：" + keyObj.get());
    }


    @Test
    public void lockTest() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);
        final String recordId = "recordId_123";
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                countDownLatch.countDown();
                log.info("开始并发执行: " + LocalTime.now());
                tryLock(recordId);
            });
        }
        log.info("线程池完成:" + LocalTime.now());
        executorService.shutdown();
        log.info("线程池退出:" + LocalTime.now());
        Thread.sleep(10000);
    }

    public void tryLock(String recordId) {
        RLock lock = redissonClient.getLock(recordId);
        try {
            boolean tryLock = lock.tryLock(5, 6, TimeUnit.SECONDS);
            if (tryLock) {
                // 业务代码
                log.info("进入业务代码: " + recordId + " : " + LocalTime.now());
                Thread.sleep(10);
            } else {
                log.info("数据已被锁定: " + recordId + " : " + LocalTime.now());
            }
        } catch (Exception e) {
            log.error("出现错误...", e);
            lock.unlock();
        }
    }

    @Test
    public void test() {
        Date date = new Date();
        LocalTime localTime = LocalTime.now();
        System.out.println("date = " + date);
        System.out.println("localTime = " + localTime);
    }

    @Test
    public void test2() {
        long l = System.currentTimeMillis();
        String s = l + "";
        System.out.println("s = " + s);
        String substring = s.substring(s.length() - 4);
        System.out.println("substring = " + substring);
    }
}
