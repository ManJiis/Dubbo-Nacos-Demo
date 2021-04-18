package top.b0x0.admin.consumer.shiro;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.b0x0.admin.common.vo.R;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 自定义shiro登录过滤器
 *
 * @author TANG
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
            R businessResponse = R.fail(401, "没有登录或登陆已过期，请重新登录");
            resp.getWriter().write(JSON.toJSONString(businessResponse));
            resp.flushBuffer();
            return false;
        }
    }

    /**
     * 配置跨域
     *
     * @param response /
     */
    private void crossDomainConfig(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        response.setHeader("Access-Control-Expose-Headers", "*");
        response.setHeader("Access-Control-Allow-Headers", "Authentication,Origin, X-Requested-With, Content-Type, " + "Accept, x-access-token");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires ", "-1");
    }
}
