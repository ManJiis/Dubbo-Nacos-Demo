package top.b0x0.admin.service.config.mybatis;

import com.alibaba.fastjson.JSON;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import top.b0x0.admin.common.pojo.system.SysUser;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

/**
 * mybatis拦截器 修改参数值
 *
 * @author musui
 * @since 2021-04-01
 */
@Intercepts(@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
public class MybatisQueryPlugin implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("=============== mybatis query interceptor START ==============");
        Object[] args = invocation.getArgs();
        if (args[0] instanceof MappedStatement) {
            MappedStatement mappedStatement = (MappedStatement) args[0];
            if (mappedStatement.getSqlCommandType() != SqlCommandType.SELECT) {
                return invocation.proceed();
            }
        } else {
            return invocation.proceed();
        }
        if (args[1] instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) args[1];
            System.out.println("mybatis interceptor start map = " + map);
            if (map.containsKey("ds")) {
                RpcContext rpcContext = RpcContext.getContext();
                String token = rpcContext.getAttachment("loginToken");
                System.out.println("loginToken token = " + token);
                String loginUser = rpcContext.getAttachment("loginUser");
                if (loginUser != null) {
                    SysUser sysUser = JSON.parseObject(loginUser, SysUser.class);
                    System.out.println("sysUser = " + sysUser);

                    Object ds = map.get("ds");
                    Field[] declaredFields = ds.getClass().getDeclaredFields();
                    for (Field field : declaredFields) {
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        if ("accountRole".equals(field.getName())) {
                            field.set(ds, "1");
                        }
                        if ("dataLevel".equals(field.getName())) {
                            field.set(ds, "1");
                        }
                    }
                }
                System.out.println("mybatis interceptor end map = " + map);
            }

        }
        return invocation.proceed();
    }

    /**
     * 生成MyBatis拦截器代理对象
     */
    @Override
    public Object plugin(Object target) {
        // 只拦截Executor对象,减少目标被代理的次数
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 设置插件属性（直接通过Spring的方式获取属性，所以这个方法一般也用不到）
     * 项目启动的时候数据就会被加载
     */
    @Override
    public void setProperties(Properties properties) {

    }
}
