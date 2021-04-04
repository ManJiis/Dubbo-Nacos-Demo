
package top.b0x0.admin.service.system;

import java.util.Map;

/**
 * @author TANG
 * @date 2020-05-02
 */
public interface MonitorService {

    /**
    * 查询数据分页
    * @return Map<String,Object>
    */
    Map<String, Object> getServers();
}
