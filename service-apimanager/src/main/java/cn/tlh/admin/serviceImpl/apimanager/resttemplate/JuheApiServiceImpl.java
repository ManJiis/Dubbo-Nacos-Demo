package cn.tlh.admin.serviceImpl.apimanager.resttemplate;

import cn.tlh.admin.common.base.common.JuheResponse;
import cn.tlh.admin.common.util.http.HttpUtil;
import cn.tlh.admin.service.JuheApiService;
import com.alibaba.fastjson.JSON;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author musui
 */
@Service(version = "${service.version}")
@Component
public class JuheApiServiceImpl implements JuheApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JuheApiServiceImpl.class);

    @Autowired
    RestTemplate restTemplate;

    static DateTimeFormatter YMDHMS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static DateTimeFormatter MD_FORMATTER = DateTimeFormatter.ofPattern("M/d");

    private static final String KEY_TODAY_IN_HISTORY = "2526ec383d550d7d2c6807286137f0a6";
    private static final String URL_TODAY_IN_HISTORY = "http://v.juhe.cn/todayOnhistory/queryEvent.php";

    /**
     * 历史上的今天
     *
     * @return /
     */
    @Override
    public JuheResponse getTodayInHistory() {
        String todayDate = MD_FORMATTER.format(LocalDate.now());
        // http://v.juhe.cn/todayOnhistory/queryEvent.php?key=YOURKEY&date=1/1
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("key", KEY_TODAY_IN_HISTORY);
        hashMap.put("date", todayDate);
        LOGGER.info("param : {}", JSON.toJSONString(hashMap));
        String sendGet = "";
        try {
            sendGet = HttpUtil.sendGet(URL_TODAY_IN_HISTORY, hashMap);
        } catch (IOException e) {
            LOGGER.error("send error{}", e.getMessage());
        }
        return JSON.parseObject(sendGet, JuheResponse.class);
    }

}
