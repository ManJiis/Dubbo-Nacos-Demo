package cn.tlh.admin.consumer.controller.system;

import cn.hutool.core.util.IdUtil;
import cn.tlh.admin.common.base.vo.BusinessResponse;
import cn.tlh.admin.common.base.vo.req.AuthUserVo;
import cn.tlh.admin.common.util.AdminConstants;
import cn.tlh.admin.common.util.RedisCacheKey;
import cn.tlh.admin.common.util.RedisTemplateUtil;
import cn.tlh.admin.consumer.aop.annotaion.rest.AnonymousGetMapping;
import cn.tlh.admin.consumer.shiro.ShiroUtils;
import cn.tlh.admin.service.system.UserService;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * 登录
 *
 * @author TANG
 * @date 2020-12-21
 */
@Slf4j
@RestController
@RequestMapping("system/auth")
@Api(tags = "系统：系统授权接口")
public class LoginController {
//    private final OnlineUserService onlineUserService;

    @Value("${loginCode.expiration}")
    private Integer expiration;

    @Reference(version = "${service.version}", check = false)
    UserService userService;
    @Autowired
    RedisTemplateUtil redisTemplateUtil;

    private String getVerifyKey(String codeType, String uuid) {
        return String.format(codeType, uuid);
    }

    @ApiOperation("登录")
    @PostMapping(value = "/login")
    public BusinessResponse login(@Validated @RequestBody AuthUserVo userVo, HttpServletRequest request) {
        // 前端传过来公钥加密的密码 这里进行解密
//        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, userVo.getPassword());
        log.info("login acount::{}", userVo.toString());
        @NotBlank(message = "登录账号不能为空") String loginAccount = userVo.getLoginAccount();
        Matcher phoneMatcher = AdminConstants.PHONE_REX.matcher(loginAccount);
        Matcher emailMatcher = AdminConstants.EMAIL_REX.matcher(loginAccount);
        // TODO 多种方式登录
        if (phoneMatcher.matches()) {
            System.out.println("登录方式: 手机号 " + loginAccount);
        } else if (emailMatcher.matches()) {
            System.out.println("登录方式: 邮箱 " + loginAccount);
        }
        // TODO 暂时不做验证码校验
/*
        // 验证码校验
        String code = (String) redisTemplateUtil.get(this.getVerifyKey(RedisCacheKey.CALCULATE_VERIFICATION_CODE, userVo.getUuid()));
        // 清除验证码
        redisTemplateUtil.delete(userVo.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BusinessErrorException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(userVo.getCode()) || !userVo.getCode().equalsIgnoreCase(code)) {
            throw new BusinessErrorException("验证码错误");
        }
*/
        // 获取shiro Subject对象
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(loginAccount, userVo.getPassword());
        // 由shiro校验
        subject.login(usernamePasswordToken);
        Map<String, Object> returnMap = new HashMap<>(2);
        returnMap.put("sessionId", subject.getSession().getId());
        returnMap.put("userInfo", userService.findByName(loginAccount));
        return BusinessResponse.ok(returnMap);
    }

    @ApiOperation("退出")
    @PostMapping("/logout")
    public BusinessResponse logout() {
        SecurityUtils.getSubject().logout();
        return BusinessResponse.ok("loginout success");
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping(value = "/currentUserInfo")
    public BusinessResponse getUserInfo() {
        return BusinessResponse.ok(ShiroUtils.getUserEntity());
    }

    @ApiOperation("获取验证码")
    @AnonymousGetMapping(value = "/code")
    public BusinessResponse getCode(String uuid) {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        System.out.println("calculate result = " + result);
        if (StringUtils.isEmpty(uuid)) {
            uuid = IdUtil.simpleUUID();
        }
        String key = this.getVerifyKey(RedisCacheKey.CALCULATE_VERIFICATION_CODE, uuid);
        // 保存
        redisTemplateUtil.set2Minutes(key, result, expiration);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2);
        imgResult.put("img", captcha.toBase64());
        imgResult.put("uuid", uuid);
        return BusinessResponse.ok(imgResult);
    }


}
