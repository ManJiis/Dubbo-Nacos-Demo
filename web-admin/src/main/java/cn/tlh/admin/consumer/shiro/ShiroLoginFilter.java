package cn.tlh.admin.consumer.shiro;

import cn.tlh.admin.common.base.common.BusinessResponse;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


/**
 * @author TANG
 * @description 自定义shiro登录过滤器
 * @date 2020-1-4
 */
public class ShiroLoginFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(ShiroLoginFilter.class);

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (this.isLoginRequest(request, response)) {
            if (this.isLoginSubmission(request, response)) {
                if (log.isTraceEnabled()) {
                    log.trace("Login submission detected.  Attempting to execute login.");
                }
                return this.executeLogin(request, response);
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("Login page view.");
                }
                return true;
            }
        } else {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            if (log.isTraceEnabled()) {
                log.trace("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [{}]", this.getLoginUrl());
            }
            // 自定义返回信息
            resp.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            resp.setContentType("application/json; charset=utf-8");
            resp.setCharacterEncoding("UTF-8");
            BusinessResponse businessResponse = BusinessResponse.fail(401, "没有登录或登陆已过期，请重新登录");
            PrintWriter out = resp.getWriter();
            out.println(JSON.toJSONString(businessResponse));
            out.flush();
            out.close();
            return false;
        }
    }
}
