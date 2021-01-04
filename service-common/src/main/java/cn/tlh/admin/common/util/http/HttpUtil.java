package cn.tlh.admin.common.util.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TANG
 * @description http工具类
 * @date 2021-1-4
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 发送GET请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> parameters) throws IOException {
        StringBuilder result = new StringBuilder();
        // 读取响应输入流
        BufferedReader in = null;
        String params = encodeParam(parameters);
        String fullUrl = url + "?" + params;
        System.out.println(fullUrl);
        // 创建URL对象
        URL connUrl = new URL(fullUrl);
        // 打开URL连接
        java.net.HttpURLConnection httpUrlConnection = (java.net.HttpURLConnection) connUrl.openConnection();
        // 设置通用属性
        httpUrlConnection.setRequestProperty("Accept", "*/*");
        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
        httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
        // 建立实际的连接
        httpUrlConnection.connect();
        // 响应头部获取
        Map<String, List<String>> headers = httpUrlConnection.getHeaderFields();
        // 遍历所有的响应头字段
        for (String key : headers.keySet()) {
            System.out.println(key + "\t：\t" + headers.get(key));
        }
        // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
        in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), StandardCharsets.UTF_8));
        String line;
        // 读取返回的内容
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        in.close();
        return result.toString();
    }

    private static String encodeParam(Map<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        // 编码之后的参数
        String params = "";
        // 编码请求参数
        if (parameters.size() == 1) {
            for (String name : parameters.keySet()) {
                sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
            }
            params = sb.toString();
        } else {
            for (String name : parameters.keySet()) {
                sb.append(name).append("=").append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8")).append("&");
            }
            String tempParams = sb.toString();
            params = tempParams.substring(0, tempParams.length() - 1);
        }
        return params;
    }

    /**
     * 发送POST请求
     *
     * @param url        目的地址
     * @param parameters 请求参数，Map类型。
     * @return /
     */
    public static String sendPost(String url, Map<String, String> parameters) throws IOException {
        // 返回的结果
        StringBuilder result = new StringBuilder();
        // 读取响应输入流
        BufferedReader in = null;
        PrintWriter out = null;
        // 编码之后的参数
        String params = encodeParam(parameters);
        // 创建URL对象
        URL connUrl = new URL(url);
        // 打开URL连接
        java.net.HttpURLConnection httpUrlConnection = (java.net.HttpURLConnection) connUrl.openConnection();
        // 设置通用属性
        httpUrlConnection.setRequestProperty("Accept", "*/*");
        httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");
        httpUrlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
        // 设置POST方式
        httpUrlConnection.setDoInput(true);
        httpUrlConnection.setDoOutput(true);
        // 获取HttpURLConnection对象对应的输出流
        out = new PrintWriter(httpUrlConnection.getOutputStream());
        // 发送请求参数
        out.write(params);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应，设置编码方式
        in = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream(), "UTF-8"));
        String line;
        // 读取返回的内容
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        out.close();
        in.close();
        return result.toString();
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，格式: name1=value1&name2=value2
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            LOGGER.error("An exception occurred when sending a GET request! {}", e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            LOGGER.error("An exception occurred when sending a POST request! {}", e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
//        Map<String, String> param = new HashMap<>();
//        String geometry = "{\"x\":106,\"y\":26,\"spatialReference\":{\"wkid\":4326}}";
//        param.put("geometry", geometry);
//        //需要添加的城市名称
//        param.put("geometryType", "esriGeometryPoint");
//        param.put("outFields", "HJYSGKFQMC, HJYSGKFQBM");
//        param.put("inSR", "4326");
//        param.put("f", "json");
//        param.put("returnGeometry", "false");
//        for (int i = 0; i < 28; i++) {
//            String s=sendGet("http://192.168.2.21:6080/arcgis/rest/services/sxydtc/MapServer/"+i+"/query", param);
//            System.out.println(s);
//        }
        Map<String, String> param = new HashMap<>();
        param.put("fileId", "ccda655772834481806f9150cfe5f39e");
        String s = sendGet("http://127.0.0.1:8089/select/selectInfoById", param);
        System.out.println(s);
    }
}
