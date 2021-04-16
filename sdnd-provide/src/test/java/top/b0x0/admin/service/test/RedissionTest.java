package top.b0x0.admin.service.test;

import top.b0x0.admin.service.SdndServiceApplication;
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
@SpringBootTest(classes = SdndServiceApplication.class)
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
        CountDownLatch startingGun = new CountDownLatch(1);
        final String recordId = "recordId_123";
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                try {
                    // 线程阻塞
                    startingGun.await();
                    tryLock(recordId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        // 线程集结完毕开始并发执行 startingGun -1
        startingGun.countDown();
        log.info("开始并发执行: " + LocalTime.now());

        // 执行完毕的线程阻塞再次等待其他线程
        countDownLatch.await();
        log.info("线程池完成:" + LocalTime.now());
        executorService.shutdown();
        log.info("线程池退出:" + LocalTime.now());
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
