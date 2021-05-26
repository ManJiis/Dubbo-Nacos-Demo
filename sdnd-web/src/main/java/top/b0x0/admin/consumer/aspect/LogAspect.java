package top.b0x0.admin.consumer.aspect;

import top.b0x0.admin.common.pojo.system.SysLog;
import top.b0x0.admin.common.util.StringUtils;
import top.b0x0.admin.common.util.ThrowableUtils;
import top.b0x0.admin.common.util.json.JackJsonUtils;
import top.b0x0.admin.consumer.annotaion.Log;
import top.b0x0.admin.consumer.shiro.ShiroUtils;
import top.b0x0.admin.service.module.system.LogService;
import org.apache.dubbo.config.annotation.Reference;
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

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Reference(version = "${service.version}", check = false)
    LogService logService;

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(top.b0x0.admin.consumer.annotaion.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param pjp join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        long currentTimeMillis = System.currentTimeMillis();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        SysLog log = new SysLog();
        log.setLogType("INFO");
        Object result;
        result = pjp.proceed();
        // =========================通用处理日志对象逻辑 start ====================================
        this.buildLog(pjp, request, log);
        // =========================通用处理日志对象逻辑 end ====================================
        long endTimeMillis = System.currentTimeMillis();
        log.setTime(endTimeMillis - currentTimeMillis);
        logService.save(log);
        return result;
    }

    /**
     * 异常通知
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
        // TODO
        String ip = "";
        String cityInfo = StringUtils.getCityInfo(ip);
        // 异常信息
        log.setExceptionDetail(ThrowableUtils.getStackTrace(e).getBytes());
        this.buildLog(jp, request, log);
        long endTimeMillis = System.currentTimeMillis();
        log.setTime(endTimeMillis - currentTimeMillis);
        logService.save(log);
    }

    /**
     * 日志对象 通用处理逻辑
     *
     * @param joinPoint /
     * @param request   /
     * @param log       /
     * @throws Exception /
     */
    private void buildLog(JoinPoint joinPoint, HttpServletRequest request, SysLog log) throws Exception {
        // 请求方法  ex: poc.representation.systemManage.UserController.queryUserLists
        String actionMethod = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
        // controller中方法名 例如: login
        String actionMethodName = joinPoint.getSignature().getName();
        // 请求方式  GET/POST/PUT/DELETE
        String requestMethod = request.getMethod();
        // 请求参数  获取用户请求方法的参数并序列化为JSON格式字符串
        StringBuilder params = new StringBuilder();
        // 参数名称
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                params.append(JackJsonUtils.toJsonString(parameterNames[i] + ":" + args[i])).append(";");
            }
        }
        // 操作描述
        String description = getAnnotationValue(joinPoint);
        // TODO
        String ip = "StringUtils.getIp(request)";
        String cityInfo = StringUtils.getCityInfo(ip);
        // TODO
        String browser = "StringUtils.getBrowser(request)";
        log.setRequestIp(ip);
        log.setAddress(cityInfo);
        log.setMethod(actionMethod);
        log.setUsername(this.getUsername());
        log.setParams(params.toString());
        log.setBrowser(browser);
        log.setDescription(description);
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
     * 获取注解中对方法的描述信息 用于Controller层注解
     */
    public static String getAnnotationValue(JoinPoint joinPoint) throws Exception {
        String targetClassName = joinPoint.getTarget().getClass().getName();
        //目标方法名
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetClassName);
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
