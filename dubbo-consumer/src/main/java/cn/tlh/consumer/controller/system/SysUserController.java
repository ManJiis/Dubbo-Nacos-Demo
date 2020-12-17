package cn.tlh.consumer.controller.system;

import cn.tlh.common.pojo.system.SysUser;
import cn.tlh.common.vo.req.UserVo;
import cn.tlh.common.vo.resp.Response;
import cn.tlh.service.system.MailService;
import cn.tlh.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 系统用户(SysUser)表控制层
 *
 * @author makejava
 * @since 2020-12-17 09:51:56
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;

    @Autowired
    private MailService mailService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public SysUser selectOne(Long id) {
        return this.userService.queryById(id);
    }


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