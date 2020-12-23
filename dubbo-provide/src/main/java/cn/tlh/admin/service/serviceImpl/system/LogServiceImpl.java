package cn.tlh.admin.service.serviceImpl.system;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.tlh.admin.common.base.vo.req.LogReqVo;
import cn.tlh.admin.common.pojo.system.SysLog;
import cn.tlh.admin.common.util.StringUtils;
import cn.tlh.admin.dao.SysLogDao;
import cn.tlh.admin.service.system.LogService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author TANG
 * @date 2020-11-24
 */
@Service(version = "${service.version}")
@Component
// @RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private static final Logger log = LoggerFactory.getLogger(LogServiceImpl.class);
//    private final LogErrorMapper logErrorMapper;
//    private final LogSmallMapper logSmallMapper;

    @Autowired(required = false)
    SysLogDao sysLogDao;

    @Override
    public List<SysLog> selectList(LogReqVo logReqVo) {
        // status: ERROR/INFO
        return sysLogDao.selectList(logReqVo);
    }

    @Override
    public Object queryAllByUser(LogReqVo logReqVo) {
        return sysLogDao.selectList(logReqVo);
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void save(SysLog log) {
        sysLogDao.insert(log);
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }

    @Override
    public Object findByErrDetail(Long id) {
        SysLog sysLog = sysLogDao.queryById(id);
        byte[] details = sysLog.getExceptionDetail();
        return Dict.create().set("exception", new String(ObjectUtil.isNotNull(details) ? details : "".getBytes()));
    }

    @Override
    public void download(List<SysLog> logs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysLog log : logs) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户名", log.getUsername());
            map.put("IP", log.getRequestIp());
            map.put("IP来源", log.getAddress());
            map.put("描述", log.getDescription());
            map.put("浏览器", log.getBrowser());
            map.put("请求耗时/毫秒", log.getTime());
            map.put("异常详情", new String(ObjectUtil.isNotNull(log.getExceptionDetail()) ? log.getExceptionDetail() : "".getBytes()));
            map.put("创建日期", log.getCreateTime());
            list.add(map);
        }
//        FileUtil.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        sysLogDao.delAllLogType("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        sysLogDao.delAllLogType("INFO");
    }
}
