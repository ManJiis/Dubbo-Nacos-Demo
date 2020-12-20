package cn.tlh.admin.service.system;

import cn.tlh.admin.common.base.vo.req.UserVo;

import javax.servlet.http.HttpSession;

/**
 * @author: TANG
 * @description: mail service
 * @date: 2020-12-09
 */
public interface MailService {
    boolean registered(UserVo userVo, HttpSession session);

    boolean sendMimeMail(String email, HttpSession session);

    boolean loginIn(String email, String password);
}
