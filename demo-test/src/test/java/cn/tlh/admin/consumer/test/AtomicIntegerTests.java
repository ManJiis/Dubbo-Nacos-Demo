package cn.tlh.admin.consumer.test;

import cn.tlh.admin.consumer.util.http.HttpUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class AtomicIntegerTests {

    @Test
    void contextLoads() {
    }

    static AtomicInteger incId = new AtomicInteger(100);

    @Test
    void threadTest() throws InterruptedException {
        //访问数
        int threadSize = 10000;
        //最大并发数量
        int poolSize = 100;
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(threadSize);
        ExecutorService executorService = new ThreadPoolExecutor(poolSize, poolSize,
                10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        //开始时间
        long beginTime = System.currentTimeMillis();
        boolean isBeginToRun = false;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < threadSize; i++) {
            //创建子线程并使其阻塞
            executorService.execute(() -> {
                try {
//                    System.out.println(Thread.currentThread().getName() + "---bigin");
                    //阻塞到countDownLatch1为0时在开始执行
                    countDownLatch1.await();
                    // TODO 需要执行的操作
                    int incrementAndGet = incId.incrementAndGet();
                    list.add(incrementAndGet);
                    System.out.println("incId = " + incrementAndGet);
//                    System.out.println(Thread.currentThread().getName() + "---END");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch2.countDown();
                }
            });
            System.out.println("结束--" + i);

        }
        //使所有的子线程开始并发执行
        countDownLatch1.countDown();
        System.out.println("线程开始执行");
        //阻塞主线程
        countDownLatch2.await();
        //结束时间
        long endTime = System.currentTimeMillis();
        //执行花费时间
        System.out.println("thread size : " + threadSize + ", use pool :" + (endTime - beginTime));
        executorService.shutdown();
        // ---------------------------------------------------------------------------------- //
        Set<Integer> set = new HashSet<>(list);
        if (list.size() == set.size()) {
            System.out.println("并未生成重复uid");
        }
    }

    @Test
    public void setAddAll() {
        List<List<String>> lists = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list2.add("1");
        list2.add("1");
        list2.add("3");
        lists.add(list1);
        lists.add(list2);
        Set<String> set = new HashSet<>();
        for (List<String> list : lists) {
            set.addAll(list);
        }
        System.out.println("set = " + set);
    }

    static AtomicInteger successCount = new AtomicInteger(0);
    static AtomicInteger failureCount = new AtomicInteger(0);

    void bachAddUser() throws InterruptedException {
        //访问数
        int threadSize = 1000;
        //最大并发数量
        int poolSize = 10;
        CountDownLatch countDownLatch1 = new CountDownLatch(1);
        CountDownLatch countDownLatch2 = new CountDownLatch(threadSize);
        ExecutorService executorService = new ThreadPoolExecutor(poolSize, poolSize,
                10L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10000), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        //开始时间
        long beginTime = System.currentTimeMillis();
        boolean isBeginToRun = false;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < threadSize; i++) {
            //创建子线程并使其阻塞
            executorService.execute(() -> {
                try {
                    //阻塞到countDownLatch1为0时在开始执行
                    countDownLatch1.await();
                    String id = HttpUtil.sendGet("http://localhost:8091/sysUser/selectMaxUserId", "");
                    Map<String, String> map = new HashMap<>();
                    map.put("id", id);
                    map.put("username", "zhubajie");
                    map.put("phone", "17688888888");
                    String result = HttpUtil.sendPost("http://localhost:8091/sysUser/addUser", map);
                    System.out.println("result.toString() = " + result.toString());
                    list.add(Integer.parseInt(id));
                    successCount.incrementAndGet();
                } catch (Exception e) {
//                    e.printStackTrace();
                    failureCount.incrementAndGet();
                } finally {
                    countDownLatch2.countDown();
                }
            });
//            System.out.println("结束--" + i);
        }
        //使所有的子线程开始并发执行
        countDownLatch1.countDown();
//        System.out.println("线程开始执行");
        //阻塞主线程
        countDownLatch2.await();
        //结束时间
        long endTime = System.currentTimeMillis();
        //执行花费时间
        System.out.println("thread size : " + threadSize + ", use pool :" + (endTime - beginTime));
        executorService.shutdown();
        // ---------------------------------------------------------------------------------- //
        Set<Integer> set = new HashSet<>(list);
        if (list.size() == set.size()) {
            System.out.println("并未生成重复uid");
        }
        System.out.println("成功数 = " + successCount.get());
        System.out.println("失败数 = " + failureCount.get());
    }

    @Autowired
    RestTemplate restTemplate;

    @Test
    void addUserTest() throws InterruptedException, IOException {
        String id = HttpUtil.sendGet("http://192.168.8.106:8091/sysUser/selectMaxUserId", "");
        System.out.println("id = " + id);
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("username", "zhubajie");
        map.put("phone", "17688888888");
        String result = HttpUtil.sendPost("http://192.168.8.106:8091/sysUser/addUser", map);
        System.out.println("result.toString() = " + result.toString());
    }

    @Test
    void insertUserTest() throws InterruptedException {

    }

    @Test
    void bachAddUser1Test() throws InterruptedException {
        bachAddUser();
    }

    @Test
    void bachAddUser2Test() throws InterruptedException {
        bachAddUser();
    }
}
