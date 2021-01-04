package cn.tlh.admin.consumer.aop.aspect;

import cn.tlh.admin.common.pojo.system.SysLog;
import cn.tlh.admin.common.util.StringUtils;
import cn.tlh.admin.common.util.ThrowableUtil;
import cn.tlh.admin.common.util.spring.RequestHolder;
import cn.tlh.admin.consumer.aop.JsonUtils;
import cn.tlh.admin.consumer.aop.annotaion.Log;
import cn.tlh.admin.consumer.shiro.ShiroUtils;
import cn.tlh.admin.service.system.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author TANG
 * @date 2020-12-18
 */
@Component
@Aspect
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    private final LogService logService;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    // 构造注入
    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(cn.tlh.admin.consumer.aop.annotaion.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param pjp join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        long currentTimeMillis = System.currentTimeMillis();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        // 创建日志对象
        SysLog log = new SysLog();
        log.setLogType("INFO");
        Object result;
        result = pjp.proceed();
        // 请求方法  poc.representation.systemManage.UserController.queryUserLists
        String actionMethod = pjp.getTarget().getClass().getName() + "." + pjp.getSignature().getName();
        // controller中方法名 例如: login
        String actionMethodName = pjp.getSignature().getName();
        // 请求方式  GET/POST/PUT/DELETE
        String requestMethod = request.getMethod();
        // 请求参数
        // 获取用户请求方法的参数并序列化为JSON格式字符串
        StringBuilder params = new StringBuilder();
        // 参数名称
        String[] parameterNames = ((MethodSignature) pjp.getSignature()).getParameterNames();
        // 参数值
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                params.append(JsonUtils.objectToJson(parameterNames[i] + ":" + args[i])).append(";");
            }
        }
        // 操作描述
        String description = getControllerMethodDescription(pjp);
        String ip = StringUtils.getIp(request);
        String cityInfo = StringUtils.getCityInfo(ip);
        String browser = StringUtils.getBrowser(request);
        currentTime.set(currentTimeMillis);
        log.setRequestIp(ip);
        log.setAddress(cityInfo);
        log.setMethod(actionMethod);
        log.setUsername(this.getUsername());
        log.setParams(params.toString());
        log.setBrowser(browser);
        log.setDescription(description);
        currentTime.remove();
        long endTimeMillis = System.currentTimeMillis();
        log.setTime(endTimeMillis - currentTimeMillis);
        logService.save(log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param jp join point for advice
     * @param e  exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint jp, Throwable e) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        SysLog log = new SysLog();
        log.setLogType("ERROR");
        String ip = StringUtils.getIp(request);
        String cityInfo = StringUtils.getCityInfo(ip);
        // 异常信息
        log.setExceptionDetail(ThrowableUtil.getStackTrace(e).getBytes());
        // 请求方法  cn.tlh.consumer.controller.system.UserController.create
        String actionMethod = jp.getTarget().getClass().getName() + "." + jp.getSignature().getName();
        // controller中方法名 例如: login
        String actionMethodName = jp.getSignature().getName();
        // 请求方式  GET/POST/PUT/DELETE
        String requestMethod = request.getMethod();
        // 请求参数
        // 获取用户请求方法的参数并序列化为JSON格式字符串
        StringBuilder params = new StringBuilder();
        // 参数名称
        String[] parameterNames = ((MethodSignature) jp.getSignature()).getParameterNames();
        // 参数值
        Object[] args = jp.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                params.append(JsonUtils.objectToJson(parameterNames[i] + ":" + args[i])).append(";");
            }
        }
        // 操作描述
        String description = getControllerMethodDescription(jp);
        currentTime.set(System.currentTimeMillis());
        log.setRequestIp(ip);
        log.setAddress(cityInfo);
        log.setMethod(actionMethod);
        log.setUsername(this.getUsername());
        log.setParams(params.toString());
        log.setBrowser(StringUtils.getBrowser(request));
        log.setDescription(description);
        currentTime.remove();
        long endTimeMillis = System.currentTimeMillis();
        log.setTime(endTimeMillis - currentTimeMillis);
        logService.save(log);
    }

    /**
     * 获取当前登录用户名
     *
     * @return username
     */
    public String getUsername() {
        try {
            return ShiroUtils.getUserEntity().getUsername();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @description: 获取注解中对方法的描述信息 用于Controller层注解
     * @date 2020-12-18
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        //目标方法名
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(Log.class).description();
                    break;
                }
            }
        }
        return description;
    }
}
