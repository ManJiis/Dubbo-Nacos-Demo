package top.b0x0.admin.consumer.filter;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import top.b0x0.admin.common.pojo.system.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 上游Filter
 * 参数由此传入RpcContext, 方便生产者接收
 *
 * @author musui
 * @since 2021/4/4
 */
@Activate(group = CommonConstants.CONSUMER)
public class UpStreamFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(UpStreamFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");
        //手动设置consumer的应用名进attachment
        String application = invoker.getUrl().getParameter(CommonConstants.APPLICATION_KEY);
        if (application != null) {
            RpcContext.getContext().setAttachment("dubboApplication", application);
            RpcContext.getContext().setAttachment("loginToken", token);
            SysUser sysUser = new SysUser();
            sysUser.setUsername("admin");
            RpcContext.getContext().setAttachment("loginUser", JSON.toJSONString(sysUser));
        }

        Result result = null;
        String serverIp = null;
        long startTime = System.currentTimeMillis();
        try {
            result = invoker.invoke(invocation);
            //这次返回结果是哪个ip
            serverIp = RpcContext.getContext().getRemoteHost();
            return result;
        } finally {
            Throwable throwable = (result == null) ? null : result.getException();
            Object resultObj = (result == null) ? null : result.getValue();
            long costTime = System.currentTimeMillis() - startTime;
            log.info("[TRACE] Call {}, {}.{}() param:{}, return:{}, exception:{}, cost:{}ms!",
                    serverIp, invoker.getInterface(), invocation.getMethodName(), invocation.getArguments(), resultObj, throwable, costTime);
        }
    }

}