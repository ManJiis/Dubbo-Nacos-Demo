package cn.tlh.admin.common.util.constant;

/**
 * @author musui
 * @description 聚合api  https://www.juhe.cn/
 */
public class JuheApiConstants {

    /**
     * 历史上的今天
     */
    public static final String KEY_TODAY_IN_HISTORY = "2526ec383d550d7d2c6807286137f0a6";
    public static final String URL_TODAY_IN_HISTORY = "http://v.juhe.cn/todayOnhistory/queryEvent.php";
    /**
     * 手机号码归属地查询
     */
    public static final String KEY_MOBILE_PHONE_NUMBER_ATTRIBUTION = "cf2f696a6521c20c927d6a2a8cff276c";
    public static final String URL_MOBILE_PHONE_NUMBER_ATTRIBUTION = "http://apis.juhe.cn/mobile/get";
    /**
     * 行政区划查询
     * 全国行政区查询,支持省、市、区（乡镇）、街道。最大4级
     */
    public static final String KEY_ADMINISTRATIVE_DIVISIONS = "db5b090cdd2516bbe38e03bd1759a531";
    public static final String URL_ADMINISTRATIVE_DIVISIONS = "http://apis.juhe.cn/xzqh/query";
    /**
     * 天气查询
     */
    public static final String KEY_SIMPLE_WEATHER = "1232417f6c9111b2e8fa6aa4ca56e2bb";
    public static final String URL_SIMPLE_WEATHER = "http://apis.juhe.cn/simpleWeather/query";
}
