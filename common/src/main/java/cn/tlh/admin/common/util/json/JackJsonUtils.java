package cn.tlh.admin.common.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * jackson 工具类
 *
 * @author musui
 */
public class JackJsonUtils {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串。
     */
    public static String toJsonString(Object data) {
        try {
            return OBJECT_MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     */
    public static <T> T toObject(String jsonData, Class<T> beanType) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     */
    public static <T> T toList(String jsonData, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}