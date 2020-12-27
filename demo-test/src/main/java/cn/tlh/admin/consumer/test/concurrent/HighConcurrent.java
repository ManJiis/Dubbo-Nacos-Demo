package cn.tlh.admin.consumer.test.concurrent;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟并发执行接口调用
 *
 * @author TANG
 */
public class HighConcurrent {
    /**
     * 访问失败次数
     */
    static int num = 0;
    /**
     * 总访问次数
     */
    static int fwnum = 0;
    /**
     * 定义线程数
     */
    int threadNum = 100;
    //    static List<Map<String,Object>> infences = new ArrayList<>();

    /**
     * 发令枪
     */
    CountDownLatch countDownLatch = new CountDownLatch(threadNum);

    public static void main(String[] args) {
        HighConcurrent h = new HighConcurrent();
        h.runThread();
    }

    public void runThread() {
        //定义线程池
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (int i = 0; i < threadNum; i++) {
            executorService.submit(buildThread());
        }
    }

    public Thread buildThread() {
        //创建线程
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (countDownLatch) {  //这一步不知道有没有必要，但是我还是加了
                    //发令枪减1
                    countDownLatch.countDown();
                }
                try {
                    System.out.println("线程：" + Thread.currentThread().getName() + "准备");
                    //线程阻塞
                    countDownLatch.await();
                    //这一步是调用线上的接口，发送HTTP请求    127.0.0.1可以换成你的ip地址     post是我的请求方式
                    Object appectContext = appectContext("http://127.0.0.1:9300/related/game/getFeatured", "post");
                    fwnum++;       //访问的总次数
                    if (appectContext == null) {
                        num++;     //访问失败的次数
                    }
                    System.out.println("接受的值" + appectContext);
                    System.out.println("错误次数" + num);
                    System.out.println("总次数" + fwnum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return thread;
    }

    public Object appectContext(String url, String states) {
        // 配置请求信息（请求时间）
        RequestConfig rc = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        // 获取使用DefaultHttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 返回结果
        String result = null;
        //请求路径不是null
        if (url != null) {
            //post请求
            if (HttpPost.METHOD_NAME.equals(states)) {
                //System.out.println("post请求");
                HttpPost httpPost = new HttpPost(url);
                httpPost.setConfig(rc);
                try {
                    CloseableHttpResponse response = httpclient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
                    result = EntityUtils.toString(entity, "UTF-8");
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //get请求
            } else if (HttpGet.METHOD_NAME.equals(states)) {
                //System.out.println("get请求");
                HttpGet httpGet = new HttpGet(url);
                httpGet.setConfig(rc);
                try {
                    return httpclient.execute(httpGet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("请求失败");
            }
        } else {
            //传的路径是null
            System.out.println("路径是null");
        }
        return null;
    }
}
