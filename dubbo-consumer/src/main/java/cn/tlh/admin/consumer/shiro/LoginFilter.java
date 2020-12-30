package cn.tlh.admin.consumer.shiro;

import cn.tlh.admin.common.base.vo.BusinessResponse;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @author TANG
 */
public class LoginFilter extends PathMatchingFilter {

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Subject subject = SecurityUtils.getSubject();
        boolean isAuthenticated = subject.isAuthenticated();
        if (!isAuthenticated) {
            BusinessResponse businessResponse = BusinessResponse.fail("401", "没有登录，请重新登录");
            PrintWriter writer = response.getWriter();
            writer.append(JSON.toJSONString(businessResponse));
            return false;
        }
        return true;
    }
}