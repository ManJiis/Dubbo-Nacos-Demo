package top.b0x0.admin.consumer.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import lombok.Data;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class FastjsonTest {
    private static SerializeConfig mapping = new SerializeConfig();

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }


    @Test
    public void dateToString() {
        Date date = new Date();
        String text = JSON.toJSONString(date, mapping);
        System.out.println(text);
    }

    @Test
    public void json2List() {
        /**List -> JSON array*/
        List<Bar> barList = new ArrayList<Bar>();
        barList.add(new Bar());
        barList.add(new Bar());
        barList.add(new Bar());
        String json = JSON.toJSONString(barList);
        System.out.println(json);
        json = JSON.toJSONString(barList, true);
        System.out.println(json);
        /**JSON array -> List*/
        List<Bar> barList1 = JSON.parseArray(json, Bar.class);
        for (Bar bar : barList1) {
            System.out.println(bar.toString());
        }
    }

    @Test
    public void json2Map() {
        //Map -> JSON
        Map<String, Bar> map = new HashMap<String, Bar>();
        map.put("a", new Bar());
        map.put("b", new Bar());
        map.put("c", new Bar());
        String json = JSON.toJSONString(map, true);
        System.out.println(json);
        //JSON -> Map
        Map<String, Bar> map1 = (Map<String, Bar>) JSON.parse(json);
        for (String key : map1.keySet()) {
            System.out.println(key + ":" + map1.get(key));
        }
    }

    @Test
    public void array2JSON() {
        String[] arr_String = {"a", "b", "c"};
        String json_arr_String = JSON.toJSONString(arr_String, true);
        System.out.println(json_arr_String);
        JSONArray jsonArray = JSON.parseArray(json_arr_String);
        for (Object o : jsonArray) {
            System.out.println(o);
        }
        System.out.println(jsonArray);
    }

    @Test
    public void array2JSON2() {
        Bar[] arr_Bar = {new Bar(), new Bar(), new Bar()};
        String json_arr_Bar = JSON.toJSONString(arr_Bar, true);
        System.out.println(json_arr_Bar);
        JSONArray jsonArray = JSON.parseArray(json_arr_Bar);
        for (Object o : jsonArray) {
            System.out.println(o);
        }
        System.out.println(jsonArray);
    }

    @Test
    public void map2JSON() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "aaa");
        map.put("b", "bbb");
        map.put("c", "ccc");
        String json = JSON.toJSONString(map);
        System.out.println(json);
        Map<String, Object> map1 = JSON.parseObject(json);
        for (Object o : map.entrySet()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) o;
            System.out.println(entry.getKey() + "--->" + entry.getValue());
        }
    }
}

@Data
class Bar {
    public static SerializeConfig mapping = new SerializeConfig();
    private String barName;
    private int barAge;
    private Date barDate = new Date();

    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
    }

    {
        Random r = new Random();
        barName = "sss_" + String.valueOf(r.nextFloat());
        barAge = r.nextInt();
    }

    public static void main(String[] args) {
        String x1 = JSON.toJSONString(new Bar(), true);
        System.out.println(x1);
        String x2 = JSON.toJSONString(new Bar(), mapping);
        System.out.println(x2);
    }
}