package cn.tlh.admin.common.util.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author musui
 */
public class HttpClientService {

    /**
     * @param reqMap  /
     * @param httpUrl /
     * @return /
     */
    public static Map<String, Object> getHttpResp(Map<String, String> reqMap, String httpUrl) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(httpUrl);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        //设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 300000);
        StringBuilder response = new StringBuilder();
        Map<String, Object> mp = new HashMap<>();
        try {
            //设置参数
            NameValuePair[] nvps = getNameValuePair(reqMap);
            method.setRequestBody(nvps);
            //执行
            int rescode = client.executeMethod(method);
            mp.put("statusCode", rescode);
            //返回状态码为200
            if (rescode == HttpStatus.SC_OK) {
                //返回数据流
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), StandardCharsets.UTF_8));
                String curline;
                while ((curline = reader.readLine()) != null) {
                    response.append(curline);
                }
                mp.put("response", response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放连接
            method.releaseConnection();
        }
        return mp;
    }

    public static NameValuePair[] getNameValuePair(Map<String, String> bean) {
        List<NameValuePair> x = new ArrayList<>();
        for (String type : bean.keySet()) {
            x.add(new NameValuePair(type, String.valueOf(bean.get(type))));
        }
        Object[] y = x.toArray();
        NameValuePair[] n = new NameValuePair[y.length];
        System.arraycopy(y, 0, n, 0, y.length);
        return n;
    }

    /**
     * post请求传输json数据
     *
     * @param url      /
     * @param json     /
     * @param encoding /
     * @return /
     * @throws ClientProtocolException /
     * @throws IOException             /
     */
    public static String sendPostDataByJson(String url, String json, String encoding) throws ClientProtocolException, IOException {
        String result = "";

        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        // 设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding("utf-8");
        httpPost.setEntity(stringEntity);

        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(httpPost);

        // 获取结果实体
        // 判断网络连接状态码是否正常(0--200都数正常)
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
        }
        // 释放链接
        response.close();

        return result;
    }

    public static JSONObject sendJson(JSONObject json, String httpUrl) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(httpUrl);
        method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        StringBuilder response = new StringBuilder();

        JSONObject retJson = new JSONObject();

        try {
            RequestEntity entity = new StringRequestEntity(json.toJSONString(), "application/json", "UTF-8");
            method.setRequestEntity(entity);
            method.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            int rescode = client.executeMethod(method);

            if (rescode == org.apache.commons.httpclient.HttpStatus.SC_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), StandardCharsets.UTF_8));
                String curline;
                while ((curline = reader.readLine()) != null) {
                    response.append(curline);
                }
                retJson = JSONObject.parseObject(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return retJson;
    }

}
