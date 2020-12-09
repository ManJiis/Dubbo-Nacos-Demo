package cn.tlh.ex.provide.serviceImpl.base;

import cn.tlh.ex.common.entity.User;
import cn.tlh.ex.common.vo.req.UserVo;
import cn.tlh.ex.dao.UserDao;
import cn.tlh.ex.service.mail.MailService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @author TANG
 */
@Service(version = "${service.version}")
@Component
public class MailServiceImpl implements MailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Autowired
    private UserDao userDao;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 给前端输入的邮箱，发送验证码
     *
     * @param email
     * @param session
     * @return
     */
    @Override
    public boolean sendMimeMail(String email, HttpSession session) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            //主题
            mailMessage.setSubject("验证码邮件");
            //生成随机数
            String code = randomCode();
            //将随机数放置到session中
            session.setAttribute("email", email);
            session.setAttribute("code", code);
            //内容
            mailMessage.setText("您收到的验证码是：" + code);
            //发给谁
            mailMessage.setTo(email);
            //你自己的邮箱
            mailMessage.setFrom(from);
            //发送
            mailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 随机生成6位数的验证码
     *
     * @return String code
     */
    public String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


    /**
     * 将表单中的对象转化为数据库中存储的用户对象（剔除表单中的code）
     *
     * @param userVo
     * @return
     */
    public User userVoToUser(UserVo userVo) {

        //创建一个数据库中存储的对象
        User user = new User();
        //传值
        user.setUsername(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        user.setEmail(userVo.getEmail());
        // 返回包装后的对象
        return user;
    }

    /**
     * 注册
     *
     * @param userVo
     * @param session
     * @return
     */
    @Override
    public boolean registered(UserVo userVo, HttpSession session) {
        //获取session中的验证信息
        String email = (String) session.getAttribute("email");
        String code = (String) session.getAttribute("code");
        //获取表单中的提交的验证信息
        String voCode = userVo.getCode();
        //如果email数据为空，或者不一致，注册失败
        if (email == null || email.isEmpty()) {
            //return "error,请重新注册";
            return false;
        } else if (!code.equals(voCode)) {
            //return "error,请重新注册";
            return false;
        }
        //保存数据
        User user = this.userVoToUser(userVo);
        //将数据写入数据库
        userDao.insertUser(user);
        //跳转成功页面
        return true;
    }

    /**
     * 通过输入email查询password，然后比较两个password，如果一样，登录成功
     *
     * @param email
     * @param password
     * @return
     */

    @Override
    public boolean loginIn(String email, String password) {
        User user = userDao.queryByEmail(email);
        if (!user.getPassword().equals(password)) {
            return false;
        }
        System.out.println("登录成功:数据库密码是：" + user.getPassword());
        return true;
    }
}
