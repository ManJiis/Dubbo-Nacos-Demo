package top.b0x0.admin.consumer.aspect;

import top.b0x0.admin.common.exception.BusinessErrorException;
import top.b0x0.admin.common.util.StringUtils;
import top.b0x0.admin.consumer.annotaion.AccessLimit;
import top.b0x0.admin.consumer.annotaion.LimitType;
import com.google.common.collect.ImmutableList;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author /
 */
@Aspect
@Component
public class AccessLimitAspect {

    private static final Logger log = LoggerFactory.getLogger(AccessLimitAspect.class);

    @Autowired(required = false)
    @Qualifier("objectRedisTemplate")
    RedisTemplate<Object, Object> objectRedisTemplate;


    @Pointcut("@annotation(top.b0x0.admin.consumer.annotaion.AccessLimit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method signatureMethod = signature.getMethod();
        AccessLimit accessLimit = signatureMethod.getAnnotation(AccessLimit.class);
        LimitType limitType = accessLimit.limitType();
        String key = accessLimit.key();
        if (limitType == LimitType.IP) {
            // TODO
            key = "StringUtils.getIp(request)";
            log.info("------------>限流 LimitType = " + LimitType.IP);
        } else {
            key = signatureMethod.getName();
            log.info("------------>限流 LimitType = " + LimitType.CUSTOMER);
        }
        log.info("------------>限流 key = " + key);

        // limit:test:_system_limit
//        String join = StringUtils.join(accessLimit.prefix(), ":", key, ":", request.getRequestURI().replace("/", "_"));
//        String joinKey = join.replaceFirst("_", "");
        String joinKey = StringUtils.join(accessLimit.keyPrefix(), ":", key, ":", request.getRequestURI());
        ImmutableList<Object> keys = ImmutableList.of(joinKey);

        String luaScript = buildLuaScript();
        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = objectRedisTemplate.execute(redisScript, keys, accessLimit.count(), accessLimit.period());
        if (null != count && count.intValue() <= accessLimit.count()) {
            log.info("第{}次访问key为 : {}，描述为 : [{}] 的接口", count, keys, accessLimit.description());
            return joinPoint.proceed();
        } else {
            throw new BusinessErrorException("访问次数受限制");
        }
    }

    /**
     * lua限流脚本
     */
    private String buildLuaScript() {
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }
}

