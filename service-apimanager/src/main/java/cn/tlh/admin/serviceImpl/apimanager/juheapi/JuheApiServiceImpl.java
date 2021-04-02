//package cn.tlh.admin.serviceImpl.apimanager.juheapi;
//
//import top.b0x0.admin.common.vo.JuheResponse;
//import top.b0x0.admin.common.util.http.HttpUtils;
//import top.b0x0.admin.service.JuheApiService;
//import com.alibaba.fastjson.JSON;
//import org.apache.dubbo.config.annotation.Service;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//
//import static top.b0x0.admin.common.util.constants.JuheApiConstants.*;
//
///**
// * @author musui
// */
//@Service(version = "${service.version}")
//@Component
//public class JuheApiServiceImpl implements JuheApiService {
//
//    private static final Logger log = LoggerFactory.getLogger(JuheApiServiceImpl.class);
//
//    @Autowired
//    RestTemplate restTemplate;
//
//    static DateTimeFormatter YMDHMS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//    static DateTimeFormatter MD_FORMATTER = DateTimeFormatter.ofPattern("M/d");
//
//    /**
//     * 历史上的今天
//     *
//     * @return /
//     */
//    @Override
//    public JuheResponse getTodayInHistory() {
//        String todayDate = MD_FORMATTER.format(LocalDate.now());
//        // http://v.juhe.cn/todayOnhistory/queryEvent.php?key=YOURKEY&date=1/1
//        Map<String, String> params = new HashMap<>();
//        params.put("key", KEY_TODAY_IN_HISTORY);
//        // 2/10
//        params.put("date", todayDate);
//        log.info("==========历史上的今天 param : {}", JSON.toJSONString(params));
//        String sendGet = "";
//        try {
//            sendGet = HttpUtils.sendGet(URL_TODAY_IN_HISTORY, params);
//        } catch (Exception e) {
//            log.error("历史上的今天 接口请求错误 : {}", e.getMessage());
//        }
//        return JSON.parseObject(sendGet, JuheResponse.class);
//    }
//
//    /**
//     * 手机号归属地查询
//     *
//     * @return /
//     */
//    @Override
//    public JuheResponse getPhoneAttribution(String phone) {
//        // http://apis.juhe.cn/mobile/get?phone=176xxxxxxxx&dtype=&key=YOURKEY
//        Map<String, String> params = new HashMap<>();
//        params.put("key", KEY_MOBILE_PHONE_NUMBER_ATTRIBUTION);
//        params.put("phone", phone);
//        // 返回数据的格式,xml或json，默认json
//        params.put("dtype", "");
//        log.info("================手机号归属地查询 param : {}", JSON.toJSONString(params));
//        String sendGet = "";
//        try {
//            sendGet = HttpUtils.sendGet(URL_MOBILE_PHONE_NUMBER_ATTRIBUTION, params);
//        } catch (Exception e) {
//            log.error("手机号归属地查询 接口请求错误 : {}", e.getMessage());
//        }
//        return JSON.parseObject(sendGet, JuheResponse.class);
//    }
//
//    /**
//     * 行政区划查询
//     * 全国行政区查询,支持省、市、区（乡镇）、街道。最大4级
//     *
//     * @param id fid  上级地区代码，0为省级，其他传相应级别id代码
//     * @return /
//     */
//    @Override
//    public JuheResponse getAdministrativeDivisions(String id) {
//        // 请求地址：http://apis.juhe.cn/xzqh/query
//        // 请求参数：fid=&key=db5b090cdd2516bbe38e03bd1759a531
//        // 请求方式：GET
//        Map<String, String> params = new HashMap<>();
//        params.put("key", KEY_ADMINISTRATIVE_DIVISIONS);
//        params.put("fid", id);
//        log.info("================行政区划查询 param : {}", JSON.toJSONString(params));
//        String sendGet = "";
//        try {
//            sendGet = HttpUtils.sendGet(URL_ADMINISTRATIVE_DIVISIONS, params);
//        } catch (Exception e) {
//            log.error("行政区划查询 接口请求错误 : {}", e.getMessage());
//        }
//        return JSON.parseObject(sendGet, JuheResponse.class);
//    }
//
//    /**
//     * 天气查询
//     *
//     * @param city 需要utf8 url encode/
//     * @return /
//     */
//    @Override
//    public JuheResponse getSimpleWeather(String city) {
//        // 请求地址：http://apis.juhe.cn/simpleWeather/query
//        // 请求参数：city=%E6%B7%B1%E5%9C%B3&key=1232417f6c9111b2e8fa6aa4ca56e2bb
//        // 请求方式：GET
//        Map<String, String> params = new HashMap<>();
//        params.put("key", KEY_SIMPLE_WEATHER);
//        params.put("city", city);
//        log.info("================天气查询 param : {}", JSON.toJSONString(params));
//        String sendGet = "";
//        try {
//            sendGet = HttpUtils.sendGet(URL_SIMPLE_WEATHER, params);
//        } catch (Exception e) {
//            log.error("天气查询 接口请求错误 : {}", e.getMessage());
//        }
//        return JSON.parseObject(sendGet, JuheResponse.class);
//    }
//}
