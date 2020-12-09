package cn.tlh.ex.consumer.controller.base;


import cn.tlh.ex.common.vo.req.UserVo;
import cn.tlh.ex.common.vo.resp.Response;
import cn.tlh.ex.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author TANG
 */
@RestController
@RequestMapping("/mail")
public class UserController {

    @Autowired
    private MailService mailService;

    @PostMapping("/sendEmail")
    public Response sendEmail(String email, HttpSession httpSession) {
        mailService.sendMimeMail(email, httpSession);
        return Response.ok();
    }

    @PostMapping("/regist")
    public Response regist(UserVo userVo, HttpSession session) {
        mailService.registered(userVo, session);
        return Response.ok();
    }

    @PostMapping("/login")
    public Response login(String email, String password) {
        mailService.loginIn(email, password);
        return Response.ok();
    }
}