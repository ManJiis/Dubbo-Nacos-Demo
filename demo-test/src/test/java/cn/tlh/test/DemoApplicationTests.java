package cn.tlh.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@SpringBootTest
class DemoApplicationTests {

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
}
