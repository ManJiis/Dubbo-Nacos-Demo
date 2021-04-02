package top.b0x0.admin.common.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * fastjson 工具类
 *
 * <p>
 * Fastjson API入口类是com.alibaba.fastjson.JSON，常用的序列化操作都可以在JSON类上的静态方法直接完成。
 * <p>
 * public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray
 * public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject
 * public static final <T> T parseObject(String text, Class<T> clazz); // 把JSON文本parse为JavaBean
 * public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray
 * public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合
 * public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本
 * public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本
 * public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray。
 * 有关类库的一些说明:
 * SerializeWriter：相当于StringBuffer
 * JSONArray：相当于List<Object>
 * JSONObject：相当于Map<String, Object>
 * JSON反序列化没有真正数组，本质类型都是List<Object>
 * </p>
 *
 * @author musui
 */
public class FastJsonUtils {
    private final static Logger log = LoggerFactory.getLogger(FastJsonUtils.class);
    private static final SerializeConfig SERIALIZE_CONFIG;

    static {
        SERIALIZE_CONFIG = new SerializeConfig();
        SERIALIZE_CONFIG.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    private static final SerializerFeature[] FEATURES = {
            // 输出空置字段
            SerializerFeature.WriteMapNullValue,
            // list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullListAsEmpty,
            // 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullNumberAsZero,
            // Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,
            // 字符类型字段如果为null，输出为""，而不是null
            SerializerFeature.WriteNullStringAsEmpty
    };

    /**
     * Object TO Json String 字符串输出
     */
    public static String toJsonString(Object object) {
        try {
            return JSON.toJSONString(object, SERIALIZE_CONFIG, FEATURES);
        } catch (Exception e) {
            log.error("JsonUtil | method=toJSON() | 对象转为Json字符串 Error！" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Object TO Json String Json-lib兼容的日期输出格式
     */
    public static String toJsonLib(Object object) {
        try {
            return JSON.toJSONString(object, SERIALIZE_CONFIG, FEATURES);
        } catch (Exception e) {
            log.error("JsonUtil | method=toJSONLib() | 对象转为Json字符串 Json-lib兼容的日期输出格式   Error！" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 转换为数组 Object
     */
    public static Object[] toArray(String text) {
        try {
            return toArray(text, null);
        } catch (Exception e) {
            log.error("JsonUtil | method=toArray() | 将json格式的数据转换为数组 Object  Error！" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 转换为数组 （可指定类型）
     */
    public static <T> Object[] toArray(String text, Class<T> clazz) {
        try {
            return JSON.parseArray(text, clazz).toArray();
        } catch (Exception e) {
            log.error("JsonUtil | method=toArray() | 将json格式的数据转换为数组 （可指定类型）   Error！" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Json 转为 Jave Bean
     */
    public static <T> T toObject(String text, Class<T> clazz) {
        try {
            return JSON.parseObject(text, clazz);
        } catch (Exception e) {
            log.error("JsonUtil | method=toBean() | Json 转为  Jave Bean  Error！" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Json 转为 Map
     */
    public static Map<?, ?> toMap(String json) {
        try {
            return JSON.parseObject(json);
        } catch (Exception e) {
            log.error("JsonUtil | method=toMap() | Json 转为   Map {},{}" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Json 转 List<Class> 可json-lib兼容的日期格式
     *
     * @return List<T> /
     */
    public static <T> List<T> toList(String text, Class<T> clazz) {
        try {
            return JSON.parseArray(text, clazz);
        } catch (Exception e) {
            log.error("JsonUtil | method=toList() | Json 转为 List {},{}" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 从json字符串获取指定key的字符串
     */
    public static Object getValueFromJson(final String json, final String key) {
        try {
            if (StringUtils.isBlank(json) || StringUtils.isBlank(key)) {
                return null;
            }
            return JSON.parseObject(json).getString(key);
        } catch (Exception e) {
            log.error("JsonUtil | method=getStringFromJson() | 从json获取指定key的字符串 Error！" + e.getMessage(), e);
        }
        return null;
    }
}
